package io.github.ponderyao.decision.entity;

/**
 * ActionStub: 动作桩
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ActionStub extends BaseEntity {

    /** 动作桩标识 */
    private Long actionStubId;
    
    /** 决策域标识 */
    private Long domainId;
    
    /** 动作编码 */
    private String actionCode;
    
    /** 动作名称 */
    private String actionName;
    
    /** 动作说明 */
    private String actionDesc;
    
    /** 动作脚本 */
    private String actionScript;

    /** 脚本方法 */
    private String scriptMethod;

    public Long getActionStubId() {
        return actionStubId;
    }

    public void setActionStubId(Long actionStubId) {
        this.actionStubId = actionStubId;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionDesc() {
        return actionDesc;
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public String getActionScript() {
        return actionScript;
    }

    public void setActionScript(String actionScript) {
        this.actionScript = actionScript;
    }

    public String getScriptMethod() {
        return scriptMethod;
    }

    public void setScriptMethod(String scriptMethod) {
        this.scriptMethod = scriptMethod;
    }
}
