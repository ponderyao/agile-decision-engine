package io.github.ponderyao.decision.entity;

/**
 * ConditionEntry: 条件项
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ConditionEntry extends BaseEntity {
    
    /** 条件项标识 */
    private Long conditionEntryId;
    
    /** 决策域标识 */
    private Long domainId;
    
    /** 决策规则标识 */
    private Long ruleId;
    
    /** 条件桩标识 */
    private Long conditionStubId;
    
    /** 条件值 */
    private String conditionValue;

    public Long getConditionEntryId() {
        return conditionEntryId;
    }

    public void setConditionEntryId(Long conditionEntryId) {
        this.conditionEntryId = conditionEntryId;
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

    public Long getConditionStubId() {
        return conditionStubId;
    }

    public void setConditionStubId(Long conditionStubId) {
        this.conditionStubId = conditionStubId;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }
}
