package io.github.ponderyao.decision.entity;

import java.util.Objects;

/**
 * DecisionRule: 决策规则
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DecisionRule extends BaseEntity {
    
    /** 决策规则标识 */
    private Long ruleId;
    
    /** 决策域标识 */
    private Long domainId;
    
    /** 决策规则名称 */
    private String ruleName;
    
    /** 决策规则说明 */
    private String ruleDesc;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }
}
