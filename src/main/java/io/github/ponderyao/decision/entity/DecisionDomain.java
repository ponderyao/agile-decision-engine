package io.github.ponderyao.decision.entity;

/**
 * DecisionDomain: 决策域
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DecisionDomain extends BaseEntity {
    
    /** 决策域标识 */
    private Long domainId;
    
    /** 决策域编码 */
    private String domainCode;
    
    /** 决策域名称 */
    private String domainName;
    
    /** 决策域说明 */
    private String domainDesc;
    
    public DecisionDomain() {
        
    }

    public DecisionDomain(String domainCode, String domainName, String domainDesc) {
        this.domainCode = domainCode;
        this.domainName = domainName;
        this.domainDesc = domainDesc;
    }

    public Long getDomainId() {
        return domainId;
    }

    public void setDomainId(Long domainId) {
        this.domainId = domainId;
    }

    public String getDomainCode() {
        return domainCode;
    }

    public void setDomainCode(String domainCode) {
        this.domainCode = domainCode;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getDomainDesc() {
        return domainDesc;
    }

    public void setDomainDesc(String domainDesc) {
        this.domainDesc = domainDesc;
    }
}
