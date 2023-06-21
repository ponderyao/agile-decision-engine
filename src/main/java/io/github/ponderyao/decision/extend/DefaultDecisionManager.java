package io.github.ponderyao.decision.extend;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.ponderyao.decision.common.constant.ExceptionConstants;
import io.github.ponderyao.decision.common.exception.PersistenceManageException;
import io.github.ponderyao.decision.entity.ActionEntry;
import io.github.ponderyao.decision.entity.ActionStub;
import io.github.ponderyao.decision.entity.ConditionEntry;
import io.github.ponderyao.decision.entity.ConditionStub;
import io.github.ponderyao.decision.entity.DecisionDomain;
import io.github.ponderyao.decision.entity.DecisionRule;
import io.github.ponderyao.decision.persistence.ActionEntryMapper;
import io.github.ponderyao.decision.persistence.ActionStubMapper;
import io.github.ponderyao.decision.persistence.ConditionEntryMapper;
import io.github.ponderyao.decision.persistence.ConditionStubMapper;
import io.github.ponderyao.decision.persistence.DecisionDomainMapper;
import io.github.ponderyao.decision.persistence.DecisionRuleMapper;

/**
 * DefaultDecisionManager: 默认决策管理实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Component
public class DefaultDecisionManager implements DecisionManager {

    private DecisionDomainMapper decisionDomainMapper;
    
    private DecisionRuleMapper decisionRuleMapper;
    
    private ConditionStubMapper conditionStubMapper;
    
    private ConditionEntryMapper conditionEntryMapper;
    
    private ActionStubMapper actionStubMapper;
    
    private ActionEntryMapper actionEntryMapper;

    @Autowired
    private void setDecisionDomainMapper(DecisionDomainMapper decisionDomainMapper) {
        this.decisionDomainMapper = decisionDomainMapper;
    }

    @Autowired
    private void setDecisionRuleMapper(DecisionRuleMapper decisionRuleMapper) {
        this.decisionRuleMapper = decisionRuleMapper;
    }

    @Autowired
    private void setConditionStubMapper(ConditionStubMapper conditionStubMapper) {
        this.conditionStubMapper = conditionStubMapper;
    }

    @Autowired
    private void setConditionEntryMapper(ConditionEntryMapper conditionEntryMapper) {
        this.conditionEntryMapper = conditionEntryMapper;
    }

    @Autowired
    private void setActionStubMapper(ActionStubMapper actionStubMapper) {
        this.actionStubMapper = actionStubMapper;
    }

    @Autowired
    private void setActionEntryMapper(ActionEntryMapper actionEntryMapper) {
        this.actionEntryMapper = actionEntryMapper;
    }
    
    @Override
    public DecisionDomain findDomain(String domainCode) {
        return decisionDomainMapper.selectByCode(domainCode);
    }

    @Override
    public List<DecisionRule> loadRules(DecisionDomain domain) {
        return decisionRuleMapper.selectListByDomain(domain.getDomainId());
    }

    @Override
    public List<ConditionStub> loadConditionStubList(DecisionDomain domain) {
        return conditionStubMapper.selectListByDomain(domain.getDomainId());
    }

    @Override
    public List<ConditionEntry> loadConditionEntryList(ConditionStub conditionStub) {
        return conditionEntryMapper.selectListByCondition(conditionStub.getDomainId(), conditionStub.getConditionStubId());
    }

    @Override
    public List<ActionEntry> loadActionEntryList(DecisionRule rule) {
        return actionEntryMapper.selectListByRule(rule.getDomainId(), rule.getRuleId());
    }

    @Override
    public List<ActionStub> loadActionStubList(DecisionRule rule) {
        return actionStubMapper.selectListByRule(rule.getDomainId(), rule.getRuleId());
    }

    /**
     * 以下方法为面向外部扩展提供
     */

    @Override
    public List<DecisionDomain> findDomainList() {
        return decisionDomainMapper.selectList();
    }

    @Override
    public void registerDomain(DecisionDomain domain) {
        if (ObjectUtils.isNotEmpty(findDomain(domain.getDomainCode()))) {
            throw new PersistenceManageException(ExceptionConstants.DECISION_DOMAIN.REPEAT, domain.getDomainCode());
        }
        decisionDomainMapper.insert(domain);
    }

    @Override
    public void modifyDomain(DecisionDomain domain) {
        DecisionDomain originDomain = decisionDomainMapper.selectById(domain.getDomainId());
        if (!StringUtils.equals(originDomain.getDomainCode(), domain.getDomainCode())) {
            if (ObjectUtils.isNotEmpty(findDomain(domain.getDomainCode()))) {
                throw new PersistenceManageException(ExceptionConstants.DECISION_DOMAIN.REPEAT, domain.getDomainCode());
            }
        }
        decisionDomainMapper.update(domain);
    }

    @Override
    public void removeDomain(DecisionDomain domain) {
        decisionDomainMapper.delete(domain);
    }

    @Override
    public void defineRule(DecisionRule rule) {
        if (ObjectUtils.isNotEmpty(decisionRuleMapper.selectRuleByName(rule.getDomainId(), rule.getRuleName()))) {
            DecisionDomain domain = decisionDomainMapper.selectById(rule.getDomainId());
            throw new PersistenceManageException(ExceptionConstants.DECISION_RULE.REPEAT, rule.getRuleName(), domain.getDomainCode());
        }
        decisionRuleMapper.insert(rule);
    }

    @Override
    public void modifyRule(DecisionRule rule) {
        DecisionRule originRule = decisionRuleMapper.selectById(rule.getRuleId());
        if (!StringUtils.equals(originRule.getRuleName(), rule.getRuleName())) {
            if (ObjectUtils.isNotEmpty(decisionRuleMapper.selectRuleByName(rule.getDomainId(), rule.getRuleName()))) {
                DecisionDomain domain = decisionDomainMapper.selectById(rule.getDomainId());
                throw new PersistenceManageException(ExceptionConstants.DECISION_RULE.REPEAT, rule.getRuleName(), domain.getDomainCode());
            }
        }
        decisionRuleMapper.update(rule);
    }

    @Override
    public void removeRule(DecisionRule rule) {
        decisionRuleMapper.delete(rule);
    }

    @Override
    public void defineCondition(ConditionStub conditionStub) {
        if (ObjectUtils.isNotEmpty(conditionStubMapper.selectByCode(conditionStub.getDomainId(), conditionStub.getConditionCode()))) {
            DecisionDomain domain = decisionDomainMapper.selectById(conditionStub.getDomainId());
            throw new PersistenceManageException(ExceptionConstants.CONDITION_STUB.REPEAT, conditionStub.getConditionCode(), domain.getDomainCode());
        }
        conditionStubMapper.insert(conditionStub);
    }

    @Override
    public void modifyCondition(ConditionStub conditionStub) {
        ConditionStub originConditionStub = conditionStubMapper.selectById(conditionStub.getConditionStubId());
        if (!StringUtils.equals(originConditionStub.getConditionCode(), conditionStub.getConditionCode())) {
            if (ObjectUtils.isNotEmpty(conditionStubMapper.selectByCode(conditionStub.getDomainId(), conditionStub.getConditionCode()))) {
                DecisionDomain domain = decisionDomainMapper.selectById(conditionStub.getDomainId());
                throw new PersistenceManageException(ExceptionConstants.CONDITION_STUB.REPEAT, conditionStub.getConditionCode(), domain.getDomainCode());
            }
        }
        conditionStubMapper.update(conditionStub);
    }

    @Override
    public void removeCondition(ConditionStub conditionStub) {
        conditionStubMapper.delete(conditionStub);
    }

    @Override
    public void realizeCondition(ConditionEntry conditionEntry) {
        if (ObjectUtils.isNotEmpty(conditionEntryMapper.selectByRuleAndCondition(conditionEntry.getDomainId(),
            conditionEntry.getRuleId(), conditionEntry.getConditionStubId()))) {
            DecisionDomain domain = decisionDomainMapper.selectById(conditionEntry.getDomainId());
            DecisionRule rule = decisionRuleMapper.selectById(conditionEntry.getRuleId());
            ConditionStub conditionStub = conditionStubMapper.selectById(conditionEntry.getConditionStubId());
            throw new PersistenceManageException(ExceptionConstants.CONDITION_ENTRY.REPEAT, domain.getDomainCode(), rule.getRuleName(), conditionStub.getConditionCode());
        }
        conditionEntryMapper.insert(conditionEntry);
    }

    @Override
    public void modifyConditionEntry(ConditionEntry conditionEntry) {
        conditionEntryMapper.update(conditionEntry);
    }

    @Override
    public void removeConditionEntry(ConditionEntry conditionEntry) {
        conditionEntryMapper.delete(conditionEntry);
    }

    @Override
    public void defineAction(ActionStub actionStub) {
        if (ObjectUtils.isNotEmpty(actionStubMapper.selectByCode(actionStub.getDomainId(), actionStub.getActionCode()))) {
            DecisionDomain domain = decisionDomainMapper.selectById(actionStub.getDomainId());
            throw new PersistenceManageException(ExceptionConstants.ACTION_STUB.REPEAT, actionStub.getActionCode(), domain.getDomainCode());
        }
        actionStubMapper.insert(actionStub);
    }

    @Override
    public void modifyAction(ActionStub actionStub) {
        ActionStub originActionStub = actionStubMapper.selectById(actionStub.getActionStubId());
        if (!StringUtils.equals(originActionStub.getActionCode(), actionStub.getActionCode())) {
            if (ObjectUtils.isNotEmpty(actionStubMapper.selectByCode(actionStub.getDomainId(), actionStub.getActionCode()))) {
                DecisionDomain domain = decisionDomainMapper.selectById(actionStub.getDomainId());
                throw new PersistenceManageException(ExceptionConstants.ACTION_STUB.REPEAT, actionStub.getActionCode(), domain.getDomainCode());
            }
        }
        actionStubMapper.update(actionStub);
    }

    @Override
    public void removeAction(ActionStub actionStub) {
        actionStubMapper.delete(actionStub);
    }

    @Override
    public void arrangeAction(ActionEntry actionEntry) {
        actionEntryMapper.insert(actionEntry);
    }

    @Override
    public void modifyActionEntry(ActionEntry actionEntry) {
        actionEntryMapper.update(actionEntry);
    }

    @Override
    public void removeActionEntry(ActionEntry actionEntry) {
        actionEntryMapper.delete(actionEntry);
    }

}
