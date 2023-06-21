package io.github.ponderyao.decision.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import io.github.ponderyao.decision.entity.DecisionDomain;
import io.github.ponderyao.decision.persistence.DecisionDomainMapper;
import io.github.ponderyao.decision.util.JdbcUtils;

/**
 * DecisionDomainMapperImpl: 决策域 数据操作接口实现
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Repository
public class DecisionDomainMapperImpl implements DecisionDomainMapper {
    
    private static final String SELECT_SQL_PREFIX = "SELECT domain_id, domain_code, domain_name, domain_desc, create_time, " 
        + "update_time, status FROM decision_domain ";
    
    @Override
    public void insert(DecisionDomain entity) {
        String sql = "INSERT INTO decision_domain(domain_code, domain_name, domain_desc) VALUES (?, ?, ?)";
        JdbcUtils.update(entity.getDomainCode(), entity.getDomainName(), entity.getDomainDesc());
    }

    @Override
    public DecisionDomain selectById(Long id) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_id = ? AND status = true";
        return JdbcUtils.select(sql, DecisionDomain.class, id);
    }

    @Override
    public List<DecisionDomain> selectList() {
        String sql = SELECT_SQL_PREFIX + "WHERE status = true ORDER BY create_time";
        return JdbcUtils.selectList(sql, DecisionDomain.class);
    }

    @Override
    public void update(DecisionDomain entity) {
        String sql = "UPDATE decision_domain SET domain_code = ?, domain_name = ?, domain_desc = ? WHERE domain_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getDomainCode(), entity.getDomainName(), entity.getDomainDesc(), entity.getDomainId());
    }

    @Override
    public void delete(DecisionDomain entity) {
        String sql = "UPDATE decision_domain SET status = false WHERE domain_id = ? AND status = true";
        JdbcUtils.update(sql, entity.getDomainId());
    }

    @Override
    public DecisionDomain selectByCode(String domainCode) {
        String sql = SELECT_SQL_PREFIX + "WHERE domain_code = ? AND status = true";
        return JdbcUtils.select(sql, DecisionDomain.class, domainCode);
    }
    
}
