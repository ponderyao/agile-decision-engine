package io.github.ponderyao.decision.entity;

/**
 * ActionEntry: 动作项
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ActionEntry extends BaseEntity {
    
    /** 动作项标识 */
    private Long actionEntryId;
    
    /** 决策域标识 */
    private Long domainId;
    
    /** 决策规则标识 */
    private Long ruleId;
    
    /** 动作桩标识 */
    private Long actionStubId;
    
    /** 后置动作 */
    private String nextAction;

    public Long getActionEntryId() {
        return actionEntryId;
    }

    public void setActionEntryId(Long actionEntryId) {
        this.actionEntryId = actionEntryId;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getActionStubId() {
        return actionStubId;
    }

    public void setActionStubId(Long actionStubId) {
        this.actionStubId = actionStubId;
    }

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }
}
