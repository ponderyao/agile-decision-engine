package io.github.ponderyao.decision.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.ponderyao.decision.entity.ConditionEntry;
import io.github.ponderyao.decision.persistence.ConditionEntryMapper;
import io.github.ponderyao.decision.util.JdbcUtils;

/**
 * ConditionEntryMapperImpl: 条件项 数据访问接口实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Repository
public class ConditionEntryMapperImpl implements ConditionEntryMapper {
    
    private static final String SELECT_SQL_PREFIX = "SELECT condition_entry_id, domain_id, rule_id, condition_stub_id, condition_value, " 
        + "create_time, update_time, status FROM condition_entry ";
    
    @Override
    public void insert(ConditionEntry entity) {
        String sql = "INSERT INTO condition_entry(domain_id, rule_id, condition_stub_id, condition_value) VALUES (?, ?, ?, ?)";
        JdbcUtils.update(sql, entity.getDomainId(), entity.getRuleId(), entity.getConditionStubId(), entity.getConditionValue());
    }

    @Override
    public ConditionEntry selectById(Long id) {
        String sql = SELECT_SQL_PREFIX + "WHERE condition_entry_id = ? AND status = true ORDER BY create_time";
        return JdbcUtils.select(sql, ConditionEntry.class, id);
    }

    @Override
    public List<ConditionEntry> selectList() {
        String sql = SELECT_SQL_PREFIX + "WHERE status = true ORDER BY create_time";
        return JdbcUtils.selectList(sql, ConditionEntry.class);
    }

    @Override
    public void update(ConditionEntry entity) {
        String sql = "UPDATE condition_entry SET condition_value = ? WHERE condition_entry_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getConditionValue());
    }

    @Override
    public void delete(ConditionEntry entity) {
        String sql = "UPDATE condition_entry SET status = false WHERE condition_entry_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getConditionEntryId());
    }

    @Override
    public List<ConditionEntry> selectListByCondition(Long domainId, Long conditionStubId) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_id = ? AND condition_stub_id = ? AND status = true ORDER BY create_time";
        return JdbcUtils.selectList(sql, ConditionEntry.class, domainId, conditionStubId);
    }

    @Override
    public ConditionEntry selectByRuleAndCondition(Long domainId, Long ruleId, Long conditionStubId) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_id = ? AND rule_id = ? AND condition_stub_id = ? AND status = true";
        return JdbcUtils.select(sql, ConditionEntry.class, domainId, ruleId, conditionStubId);
    }
}
