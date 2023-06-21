package io.github.ponderyao.decision.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.ponderyao.decision.entity.ActionStub;
import io.github.ponderyao.decision.persistence.ActionStubMapper;
import io.github.ponderyao.decision.util.JdbcUtils;

/**
 * ActionStubMapperImpl: 动作桩 数据访问接口实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Repository
public class ActionStubMapperImpl implements ActionStubMapper {
    
    private static final String SELECT_SQL_PREFIX = "SELECT action_stub_id, domain_id, action_code, action_name, action_desc, action_script, " 
        + "script_method, create_time, update_time, status FROM action_stub";

    @Override
    public void insert(ActionStub entity) {
        String sql = "INSERT INTO action_stub(domain_id, action_code, action_name, action_desc, action_script, script_method) VALUES (?, ?, ?, ?, ?, ?)";
        JdbcUtils.update(sql, entity.getDomainId(), entity.getActionCode(), entity.getActionName(), entity.getActionDesc(), 
            entity.getActionScript(), entity.getScriptMethod());
    }

    @Override
    public ActionStub selectById(Long id) {
        String sql = SELECT_SQL_PREFIX + "WHERE action_stub_id = ? AND status = true";
        return JdbcUtils.select(sql, ActionStub.class, id);
    }

    @Override
    public List<ActionStub> selectList() {
        String sql = SELECT_SQL_PREFIX + "WHERE status = true";
        return JdbcUtils.selectList(sql, ActionStub.class);
    }

    @Override
    public void update(ActionStub entity) {
        String sql = "UPDATE action_stub SET action_code = ? AND action_name = ? AND action_desc = ? AND action_script = ? " 
            + "AND script_method = ? WHERE action_stub_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getActionCode(), entity.getActionName(), entity.getActionDesc(), entity.getActionScript(), 
            entity.getScriptMethod(), entity.getActionStubId());
    }

    @Override
    public void delete(ActionStub entity) {
        String sql = "UPDATE action_stub SET status = false WHERE action_stub_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getActionStubId());
    }

    @Override
    public List<ActionStub> selectListByRule(Long domainId, Long ruleId) {
        String sql = "SELECT a.action_stub_id, a.domain_id, a.action_code, a.action_name, a.action_desc, a.action_script, a.script_method, a.create_time, " 
            + "a.update_time, a.status FROM action_stub a LEFT JOIN action_entry b ON a.domain_id = b.domain_id AND a.action_stub_id = b.action_stub_id " 
            + "WHERE a.domain_id = ? AND b.rule_id = ? AND a.status = true GROUP BY a.action_stub_id";
        return JdbcUtils.selectList(sql, ActionStub.class, domainId, ruleId);
    }

    @Override
    public ActionStub selectByCode(Long domainId, String actionCode) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_id = ? AND action_code = ? AND status = true";
        return JdbcUtils.select(sql, ActionStub.class, domainId, actionCode);
    }
}
