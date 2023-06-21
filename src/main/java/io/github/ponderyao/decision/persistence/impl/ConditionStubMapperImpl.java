package io.github.ponderyao.decision.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.ponderyao.decision.entity.ConditionStub;
import io.github.ponderyao.decision.persistence.ConditionStubMapper;
import io.github.ponderyao.decision.util.JdbcUtils;

/**
 * ConditionStubMapperImpl: 条件桩 数据操作接口实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Repository
public class ConditionStubMapperImpl implements ConditionStubMapper {
    
    private static final String SELECT_SQL_PREFIX = "SELECT condition_stub_id, domain_id, condition_code, condition_name, condition_type, " 
        + "condition_desc, condition_script, script_method, prev_condition, create_time, update_time, status FROM condition_stub ";
    
    @Override
    public void insert(ConditionStub entity) {
        String sql = "INSERT INTO condition_stub(domain_id, condition_code, condition_name, condition_type, condition_desc, " 
            + "condition_script, script_method, prev_condition) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        JdbcUtils.update(sql, entity.getDomainId(), entity.getConditionCode(), entity.getConditionName(), entity.getConditionDesc(), 
            entity.getConditionScript(), entity.getScriptMethod(), entity.getPrevCondition());
    }

    @Override
    public ConditionStub selectById(Long id) {
        String sql = SELECT_SQL_PREFIX + "WHERE condition_stub_id = ? AND status = true";
        return JdbcUtils.select(sql, ConditionStub.class, id);
    }

    @Override
    public List<ConditionStub> selectList() {
        String sql = SELECT_SQL_PREFIX + "WHERE status = true ORDER BY create_time";
        return JdbcUtils.selectList(sql, ConditionStub.class);
    }

    @Override
    public void update(ConditionStub entity) {
        String sql = "UPDATE condition_stub SET condition_code = ? AND condition_name = ? AND condition_type AND condition_desc "
            + "AND condition_script = ? AND script_method = ? AND prev_condition = ? WHERE condition_stub_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getConditionCode(), entity.getConditionName(), entity.getConditionType(), entity.getConditionDesc(), 
            entity.getConditionScript(), entity.getScriptMethod(), entity.getPrevCondition(), entity.getConditionStubId());
    }

    @Override
    public void delete(ConditionStub entity) {
        String sql = "UPDATE condition_stub SET status = false WHERE condition_stub_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getConditionStubId());
    }

    @Override
    public List<ConditionStub> selectListByDomain(Long domainId) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_id = ? AND status = true ORDER BY create_time";
        return JdbcUtils.selectList(sql, ConditionStub.class, domainId);
    }

    @Override
    public ConditionStub selectByCode(Long domainId, String conditionCode) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_id = ? AND condition_code = ? AND status = true";
        return JdbcUtils.select(sql, ConditionStub.class, domainId, conditionCode);
    }
}
