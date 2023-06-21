package io.github.ponderyao.decision.engine;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import io.github.ponderyao.decision.chain.ActivityChain;
import io.github.ponderyao.decision.chain.DynamicDecisionChain;
import io.github.ponderyao.decision.common.constant.ExceptionConstants;
import io.github.ponderyao.decision.common.exception.DecisionEngineException;
import io.github.ponderyao.decision.entity.ActionEntry;
import io.github.ponderyao.decision.entity.ActionStub;
import io.github.ponderyao.decision.entity.ConditionStub;
import io.github.ponderyao.decision.entity.DecisionDomain;
import io.github.ponderyao.decision.entity.DecisionRule;
import io.github.ponderyao.decision.extend.DecisionManager;
import io.github.ponderyao.decision.value.DecisionCondition;
import io.github.ponderyao.decision.value.DecisionResult;

/**
 * AgileDecisionEngine: 敏捷决策引擎
 * 
 * 特性：零代码迭代成本的敏捷策略发布
 * 本质：基于持久化决策表的动态编程实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Component
public class AgileDecisionEngine implements DecisionEngine {

    public static final Logger log = LoggerFactory.getLogger(AgileDecisionEngine.class);
    
    private final DecisionManager decisionManager;
    
    @Autowired
    public AgileDecisionEngine(DecisionManager decisionManager) {
        this.decisionManager = decisionManager;
    }

    @Override
    public DecisionResult execute(DecisionCondition condition) {
        // 1. 解析决策域，得到决策域标识
        DecisionDomain domain = analyzeDomain(condition.getDomainCode());
        // 2. 查询条件桩，得到所有预执行条件脚本
        List<ConditionStub> conditionStubList = collectCondition(domain);
        // 3. 组装决策链，动态执行条件脚本，按条件值匹配条件项，得到决策规则
        DecisionRule rule = matchPolicy(condition, domain, conditionStubList);
        if (ObjectUtils.isEmpty(rule)) {
            return DecisionResult.fail("no rules matched");
        }
        // 4. 根据决策规则查询动作项与动作桩
        List<ActionEntry> actionEntryList = collectActionEntry(domain, rule);
        List<ActionStub> actionStubList = collectActionStub(rule);
        // 5. 组装作业链，链式执行动作脚本，得到执行结果
        Map<String, Object> actionResult = runAction(condition, actionStubList, actionEntryList);
        return DecisionResult.success(rule.getRuleName(), actionResult);
    }

    /**
     * 解析决策域，根据编码获取决策域
     */
    private DecisionDomain analyzeDomain(String domainCode) {
        if (StringUtils.isBlank(domainCode)) {
            throw new DecisionEngineException(ExceptionConstants.DECISION_DOMAIN.MISSING);
        }
        DecisionDomain domain = decisionManager.findDomain(domainCode);
        if (ObjectUtils.isEmpty(domain)) {
            throw new DecisionEngineException(ExceptionConstants.DECISION_DOMAIN.NOT_EXIST, domainCode);
        }
        return domain;
    }

    /**
     * 搜集决策域包含的条件桩
     */
    private List<ConditionStub> collectCondition(DecisionDomain domain) {
        List<ConditionStub> conditionStubList = decisionManager.loadConditionStubList(domain);
        if (CollectionUtils.isEmpty(conditionStubList)) {
            throw new DecisionEngineException(ExceptionConstants.CONDITION_STUB.EMPTY, domain.getDomainCode());
        }
        return conditionStubList;
    }

    /**
     * 以链式执行脚本的形式进行决策匹配
     */
    private DecisionRule matchPolicy(DecisionCondition condition, DecisionDomain domain, List<ConditionStub> conditionStubList) {
        DynamicDecisionChain decisionChain = new DynamicDecisionChain(domain, condition, conditionStubList);
        return decisionChain.invoke();
    }

    /**
     * 搜集决策规则指向的动作项
     */
    private List<ActionEntry> collectActionEntry(DecisionDomain domain, DecisionRule rule) {
        List<ActionEntry> actionEntryList = decisionManager.loadActionEntryList(rule);
        if (CollectionUtils.isEmpty(actionEntryList)) {
            throw new DecisionEngineException(ExceptionConstants.ACTION_ENTRY.EMPTY, domain.getDomainCode(), rule.getRuleName());
        }
        return actionEntryList;
    }

    /**
     * 搜集决策规则指向的动作桩
     */
    private List<ActionStub> collectActionStub(DecisionRule rule) {
        return decisionManager.loadActionStubList(rule);
    }

    /**
     * 执行动作
     */
    private Map<String, Object> runAction(DecisionCondition condition, List<ActionStub> actionStubList, List<ActionEntry> actionEntryList) {
        ActivityChain activityChain = new ActivityChain(condition, actionStubList, actionEntryList);
        return activityChain.invoke();
    }
    
}
