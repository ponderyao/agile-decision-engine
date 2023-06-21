package io.github.ponderyao.decision.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.ponderyao.decision.entity.ActionEntry;
import io.github.ponderyao.decision.persistence.ActionEntryMapper;
import io.github.ponderyao.decision.util.JdbcUtils;

/**
 * ActionEntryMapperImpl: 动作项 数据访问接口实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Repository
public class ActionEntryMapperImpl implements ActionEntryMapper {
    
    private static final String SELECT_SQL_PREFIX = "SELECT action_entry_id, domain_id, rule_id, action_stub_id, next_action, " 
        + "create_time, update_time, status FROM action_entry ";

    @Override
    public void insert(ActionEntry entity) {
        String sql = "INSERT INTO action_entry(domain_id, rule_id, action_stub_id, next_action) VALUES (?, ?, ?, ?)";
        JdbcUtils.update(sql, entity.getDomainId(), entity.getRuleId(), entity.getActionStubId(), entity.getNextAction());
    }

    @Override
    public ActionEntry selectById(Long id) {
        String sql = SELECT_SQL_PREFIX + "WHERE action_entry_id = ? AND status = true";
        return JdbcUtils.select(sql, ActionEntry.class, id);
    }

    @Override
    public List<ActionEntry> selectList() {
        String sql = SELECT_SQL_PREFIX + "WHERE status = true";
        return JdbcUtils.selectList(sql, ActionEntry.class);
    }

    @Override
    public void update(ActionEntry entity) {
        String sql = "UPDATE action_entry SET next_action = ? WHERE action_entry_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getNextAction());
    }

    @Override
    public void delete(ActionEntry entity) {
        String sql = "UPDATE action_entry SET status = false WHERE action_entry_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getActionEntryId());
    }

    @Override
    public List<ActionEntry> selectListByRule(Long domainId, Long ruleId) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_id = ? AND rule_id = ? AND status = true";
        return JdbcUtils.selectList(sql, ActionEntry.class, domainId, ruleId);
    }
}
