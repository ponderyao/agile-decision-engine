package io.github.ponderyao.decision.persistence;

import java.util.List;

import io.github.ponderyao.decision.entity.DecisionRule;

/**
 * DecisionRuleMapper: 决策规则 数据访问接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface DecisionRuleMapper extends BaseMapper<DecisionRule> {
    
    List<DecisionRule> selectListByDomain(Long domainId);
    
    DecisionRule selectRuleByName(Long domainId, String ruleName);
    
}
