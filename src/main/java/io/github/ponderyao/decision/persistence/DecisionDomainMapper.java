package io.github.ponderyao.decision.persistence;

import io.github.ponderyao.decision.entity.DecisionDomain;

/**
 * DecisionDomainMapper: 决策域 数据操作接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface DecisionDomainMapper extends BaseMapper<DecisionDomain> {
    
    DecisionDomain selectByCode(String domainCode);
    
}
