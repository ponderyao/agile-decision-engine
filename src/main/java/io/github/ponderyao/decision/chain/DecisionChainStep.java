package io.github.ponderyao.decision.chain;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import io.github.ponderyao.decision.common.constant.ExceptionConstants;
import io.github.ponderyao.decision.convert.ValueTypeConvertService;
import io.github.ponderyao.decision.convert.WrapperValueType;
import io.github.ponderyao.decision.common.exception.ScriptEngineException;
import io.github.ponderyao.decision.engine.ScriptEngine;
import io.github.ponderyao.decision.entity.ConditionEntry;
import io.github.ponderyao.decision.entity.ConditionStub;
import io.github.ponderyao.decision.entity.DecisionRule;
import io.github.ponderyao.decision.util.MapUtils;
import io.github.ponderyao.decision.util.SpringBeanUtils;
import io.github.ponderyao.decision.value.ScriptRequest;
import io.github.ponderyao.decision.value.ScriptResult;

/**
 * DecisionChainStep: 决策链环节
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DecisionChainStep {
    
    /** 条件桩 */
    private final ConditionStub conditionStub;
    
    /** 条件项列表 */
    private final List<ConditionEntry> conditionEntryList;
    
    /** 规则ID与规则映射集 */
    private Map<Long, DecisionRule> ruleMap;
    
    public DecisionChainStep(ConditionStub conditionStub, List<ConditionEntry> conditionEntryList, Set<DecisionRule> defaultRulePool) {
        this.conditionStub = conditionStub;
        this.conditionEntryList = conditionEntryList;
        initRuleMap(defaultRulePool);
    }
    
    private void initRuleMap(Set<DecisionRule> rulePool) {
        Set<Long> ruleIdSet = conditionEntryList.stream().map(ConditionEntry::getRuleId).collect(Collectors.toSet());
        this.ruleMap = rulePool.stream().filter(rule -> ruleIdSet.contains(rule.getRuleId()))
            .collect(Collectors.toMap(DecisionRule::getRuleId, Function.identity()));
    }
    
    public Set<DecisionRule> invoke(DynamicDecisionChain decisionChain, ScriptEngine scriptEngine) {
        if (!necessaryToInvoke(decisionChain.getRulePool())) {
            decisionChain.reduceConditionPool(this.conditionStub);
            return decisionChain.getRulePool();
        }
        
        // 执行脚本
        ScriptRequest scriptRequest = new ScriptRequest(this.conditionStub.getConditionScript(), this.conditionStub.getScriptMethod(), 
            decisionChain.getPreviousObjects(), null);
        ScriptResult result = scriptEngine.execute(scriptRequest);
        if (!result.getSuccess()) {
            throw new ScriptEngineException(this.conditionStub.getConditionCode(), result.getErrMsg());
        }
        if (result.getResponseData().isEmpty()) {
            throw new ScriptEngineException(this.conditionStub.getConditionCode(), ExceptionConstants.SCRIPT_CONDITION.RESULT_EMPTY, this.conditionStub.getConditionCode());
        }
        
        return matchRules(result.getResponseData());
    }

    /**
     * 判断是否需要执行环节: 当且仅当条件项列表所属规则集合与规则池无交集时不需要执行
     */
    public boolean necessaryToInvoke(Set<DecisionRule> rulePool) {
        Set<DecisionRule> intersectionSet = rulePool.stream().filter(rule -> this.ruleMap.containsKey(rule.getRuleId())).collect(Collectors.toSet());
        if (!CollectionUtils.isEmpty(intersectionSet)) {
            Set<Long> ruleSetContainCondition = this.conditionEntryList.stream().map(ConditionEntry::getRuleId).collect(Collectors.toSet());
            return intersectionSet.stream().anyMatch(rule -> ruleSetContainCondition.contains(rule.getRuleId()));
        }
        return false;
    }

    /**
     * 根据脚本执行结果与条件项比对，返回符合的决策规则集合
     */
    private Set<DecisionRule> matchRules(Map<String, Object> conditionValue) {
        Set<DecisionRule> matchedRuleSet = new HashSet<>(); 
        ValueTypeConvertService convertService = SpringBeanUtils.getBean(ValueTypeConvertService.class);
        String conditionType = this.conditionStub.getConditionType();
        for (ConditionEntry entry : this.conditionEntryList) {
            Object targetValue = convertService.convert(entry.getConditionValue(), conditionType);
            if (StringUtils.equals(WrapperValueType.MAP.getCode(), conditionType)) {
                if (conditionValue.entrySet().containsAll(MapUtils.toLinkedHashMap(targetValue).entrySet())) {
                    matchedRuleSet.add(this.ruleMap.get(entry.getRuleId()));
                }
            } else {
                if (Objects.equals(conditionValue.get(this.conditionStub.getConditionCode()), targetValue)) {
                    matchedRuleSet.add(this.ruleMap.get(entry.getRuleId()));
                }
            }
        }
        return matchedRuleSet;
    }

    public ConditionStub getConditionStub() {
        return conditionStub;
    }

    public List<ConditionEntry> getConditionEntryList() {
        return conditionEntryList;
    }
}
