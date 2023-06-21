package io.github.ponderyao.decision.chain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import io.github.ponderyao.decision.common.constant.ExceptionConstants;
import io.github.ponderyao.decision.common.exception.DecisionEngineException;
import io.github.ponderyao.decision.engine.ScriptEngine;
import io.github.ponderyao.decision.entity.ConditionEntry;
import io.github.ponderyao.decision.entity.ConditionStub;
import io.github.ponderyao.decision.entity.DecisionDomain;
import io.github.ponderyao.decision.entity.DecisionRule;
import io.github.ponderyao.decision.extend.DecisionManager;
import io.github.ponderyao.decision.util.SpringBeanUtils;
import io.github.ponderyao.decision.value.DecisionCondition;

/**
 * DynamicDecisionChain: 动态决策链
 * 
 * 决策链是条件脚本执行链路的简称, 即决策过程中按一定的顺序执行条件脚本. 
 * 尽管条件之间执行顺序无强依赖性, 但组件允许为单一条件设定多个前置条件，
 * 因此根据前置特性可定义一条决策链.
 * 
 * 动态决策链是在决策链的基础上赋予动态缩减条件的优化特性. 对于同一决策
 * 域 (DecisionDomain), 不同的决策规则 (DecisionRule) 允许绑定不同
 * 的条件桩 (ConditionStub)与取值, 即条件项 (ConditionEntry) 组合,
 * 因此, 当执行第 i (1 <= i < 条件桩数) 个脚本时, 可以按先历规则的绑
 * 定条件筛选来提前判断下一条件脚本是否有必要执行, 以此缩减所需条件脚本
 * 的无意义执行耗时.
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DynamicDecisionChain {
    
    /** 决策环节队列 */
    private Queue<DecisionChainStep> stepQueue;
    
    /** 规则池 */
    private Set<DecisionRule> rulePool;
    
    /** 条件池 */
    private final Set<ConditionStub> conditionPool;
    
    /** 前置对象参数 */
    private final Map<String, Object> previousObjects;
    
    public DynamicDecisionChain(DecisionDomain domain, DecisionCondition condition, List<ConditionStub> conditionStubList) {
        this.previousObjects = condition.getPreviousObjects();
        this.conditionPool = new HashSet<>(conditionStubList);
        DecisionManager decisionManager = SpringBeanUtils.getBean(DecisionManager.class);
        initRulePool(domain, decisionManager);
        initStepQueue(domain, conditionStubList, decisionManager);
    }

    /**
     * 初始化规则池，根据决策域加载
     */
    private void initRulePool(DecisionDomain domain, DecisionManager decisionManager) {
        List<DecisionRule> decisionRules = decisionManager.loadRules(domain);
        if (CollectionUtils.isEmpty(decisionRules)) {
            throw new DecisionEngineException(ExceptionConstants.DECISION_RULE.EMPTY, domain.getDomainCode());
        }
        this.rulePool = new HashSet<>(decisionRules);
    }

    /**
     * 初始化决策链环节
     */
    private void initStepQueue(DecisionDomain domain, List<ConditionStub> conditionStubList, DecisionManager decisionManager) {
        this.stepQueue = new LinkedList<>();
        topologicalSort(conditionStubList);
        conditionStubList.forEach(conditionStub -> {
            List<ConditionEntry> conditionEntryList = decisionManager.loadConditionEntryList(conditionStub);
            if (!CollectionUtils.isEmpty(conditionEntryList)) {
                DecisionChainStep chainStep = new DecisionChainStep(conditionStub, conditionEntryList, this.rulePool);
                stepQueue.offer(chainStep);
            } else {
                // 不存在条件项的条件桩直接排除
                this.conditionPool.remove(conditionStub);
            }
        });
        if (stepQueue.isEmpty()) {
            throw new DecisionEngineException(ExceptionConstants.CONDITION_ENTRY.EMPTY, domain.getDomainCode());
        }
    }

    /**
     * 拓扑排序
     */
    private void topologicalSort(List<ConditionStub> conditionStubList) {
        Map<Long, ConditionStub> conditionStubMap = conditionStubList.stream()
            .collect(Collectors.toMap(ConditionStub::getConditionStubId, Function.identity()));
        Map<Long, Integer> indegree = new HashMap<>();
        List<ConditionStub> sortedList = new ArrayList<>();
        Map<Long, List<Long>> conditionToGraph = new HashMap<>();
        conditionStubList.forEach(conditionStub -> {
            Long key = conditionStub.getConditionStubId();
            if (!indegree.containsKey(key)) {
                indegree.put(key, 0);
            }
            if (!ObjectUtils.isEmpty(conditionStub.getPrevCondition())) {
                List<Long> prevConditions = Arrays.stream(conditionStub.getPrevCondition().split(","))
                    .map(Long::valueOf).collect(Collectors.toList());
                indegree.put(key, prevConditions.size());
                if (!CollectionUtils.isEmpty(prevConditions)) {
                    prevConditions.forEach(prevCondition -> {
                        if (!conditionToGraph.containsKey(prevCondition)) {
                            conditionToGraph.put(prevCondition, new ArrayList<>());
                        }
                        conditionToGraph.get(prevCondition).add(key);
                    });
                }
            }
        });
        Queue<ConditionStub> tempQueue = new LinkedList<>();
        indegree.forEach((key, value) -> {
            if (value == 0) {
                tempQueue.offer(conditionStubMap.get(key));
            }
        });
        while (!tempQueue.isEmpty()) {
            ConditionStub conditionStub = tempQueue.poll();
            sortedList.add(conditionStub);
            Long key = conditionStub.getConditionStubId();
            if (conditionToGraph.containsKey(key)) {
                conditionToGraph.get(key).forEach(nextId -> tempQueue.offer(conditionStubMap.get(nextId)));
            }
        }
        conditionStubList.clear();
        conditionStubList.addAll(sortedList);
    }
    
    public DecisionRule invoke() {
        ScriptEngine scriptEngine = SpringBeanUtils.getBean(ScriptEngine.class);
        while (!stepQueue.isEmpty()) {
            DecisionChainStep currStep = stepQueue.poll();
            // 执行当前环节
            Set<DecisionRule> matchedRules = currStep.invoke(this, scriptEngine);
            dynamicReduceRulePool(matchedRules);
            if (CollectionUtils.isEmpty(this.rulePool)) {
                return null;
            }
        }
        if (this.rulePool.size() > 1) {
            // 不允许存在多条条件项适配的规则
            Set<String> allCondition = this.conditionPool.stream().map(ConditionStub::getConditionCode).collect(Collectors.toSet());
            throw new DecisionEngineException(ExceptionConstants.DECISION_RULE.TOO_MUCH, StringUtils.join(allCondition, ","));
        }
        return this.rulePool.iterator().next();
    }

    /**
     * 动态缩减规则池
     */
    private void dynamicReduceRulePool(Set<DecisionRule> matchedRules) {
        if (CollectionUtils.isEmpty(matchedRules)) {
            this.rulePool.clear();
        }
        this.rulePool.removeIf(rule -> !matchedRules.contains(rule));
    }

    /**
     * 缩减条件池
     */
    public void reduceConditionPool(ConditionStub conditionStub) {
        this.conditionPool.remove(conditionStub);
    }

    public Queue<DecisionChainStep> getStepQueue() {
        return stepQueue;
    }

    public Set<DecisionRule> getRulePool() {
        return rulePool;
    }

    public Set<ConditionStub> getConditionPool() {
        return conditionPool;
    }

    public Map<String, Object> getPreviousObjects() {
        return previousObjects;
    }
}
