package io.github.ponderyao.decision.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.ponderyao.decision.entity.DecisionRule;
import io.github.ponderyao.decision.persistence.DecisionRuleMapper;
import io.github.ponderyao.decision.util.JdbcUtils;

/**
 * DecisionRuleMapperImpl: 决策规则 数据访问接口实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Repository
public class DecisionRuleMapperImpl implements DecisionRuleMapper {

    private static final String SELECT_SQL_PREFIX = "SELECT rule_id, domain_id, rule_name, rule_desc, create_time, "
        + "update_time, status FROM decision_rule ";

    @Override
    public void insert(DecisionRule entity) {
        String sql = "INSERT INTO decision_rule(domain_id, rule_name, rule_desc) VALUES (?, ?, ?)";
        JdbcUtils.update(sql, entity.getDomainId(), entity.getRuleName(), entity.getRuleDesc());
    }

    @Override
    public DecisionRule selectById(Long id) {
        String sql = SELECT_SQL_PREFIX + "WHERE rule_id = ? status = true ORDER BY create_time";
        return JdbcUtils.select(sql, DecisionRule.class, id);
    }

    @Override
    public List<DecisionRule> selectList() {
        String sql = SELECT_SQL_PREFIX + "WHERE status = true ORDER BY create_time";
        return JdbcUtils.selectList(sql, DecisionRule.class);
    }

    @Override
    public void update(DecisionRule entity) {
        String sql = "UPDATE decision_rule SET rule_name = ? AND rule_desc = ? WHERE rule_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getRuleName(), entity.getRuleDesc(), entity.getRuleId());
    }

    @Override
    public void delete(DecisionRule entity) {
        String sql = "UPDATE decision_rule SET status = false WHERE rule_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getRuleId());
    }

    @Override
    public List<DecisionRule> selectListByDomain(Long domainId) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_id = ? AND status = true ORDER BY create_time";
        return JdbcUtils.selectList(sql, DecisionRule.class, domainId);
    }

    @Override
    public DecisionRule selectRuleByName(Long domainId, String ruleName) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_id = ? AND rule_name = ? AND status = true";
        return JdbcUtils.select(sql, DecisionRule.class, domainId, ruleName);
    }

}
