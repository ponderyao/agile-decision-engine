package io.github.ponderyao.decision.entity;

/**
 * ConditionStub: 条件桩
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ConditionStub extends BaseEntity {
    
    /** 条件桩标识 */
    private Long conditionStubId;
    
    /** 决策域标识 */
    private Long domainId;
    
    /** 条件编码 */
    private String conditionCode;
    
    /** 条件名称 */
    private String conditionName;
    
    /** 条件类型 */
    private String conditionType;
    
    /** 条件说明 */
    private String conditionDesc;
    
    /** 条件脚本 */
    private String conditionScript;
    
    /** 脚本方法 */
    private String scriptMethod;
    
    /** 前置条件 */
    private String prevCondition;

    public Long getConditionStubId() {
        return conditionStubId;
    }

    public void setConditionStubId(Long conditionStubId) {
        this.conditionStubId = conditionStubId;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public String getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(String conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getConditionDesc() {
        return conditionDesc;
    }

    public void setConditionDesc(String conditionDesc) {
        this.conditionDesc = conditionDesc;
    }

    public String getConditionScript() {
        return conditionScript;
    }

    public void setConditionScript(String conditionScript) {
        this.conditionScript = conditionScript;
    }

    public String getScriptMethod() {
        return scriptMethod;
    }

    public void setScriptMethod(String scriptMethod) {
        this.scriptMethod = scriptMethod;
    }

    public String getPrevCondition() {
        return prevCondition;
    }

    public void setPrevCondition(String prevCondition) {
        this.prevCondition = prevCondition;
    }
}
