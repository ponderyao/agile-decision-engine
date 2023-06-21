package io.github.ponderyao.decision.value;

import java.util.Map;

/**
 * DecisionCondition: 决策条件
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DecisionCondition {
    
    /** 决策域编码 */
    private String domainCode;
    
    /** 前置对象 */
    private Map<String, Object> previousObjects;
    
    public DecisionCondition(String domainCode) {
        this(domainCode, null);
    }
    
    public DecisionCondition(String domainCode, Map<String, Object> previousObjects) {
        this.domainCode = domainCode;
        this.previousObjects = previousObjects;
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }

    public Map<String, Object> getPreviousObjects() {
        return previousObjects;
    }

    public void setPreviousObjects(Map<String, Object> previousObjects) {
        this.previousObjects = previousObjects;
    }
}
