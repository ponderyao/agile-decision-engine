package io.github.ponderyao.decision.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * JdbcUtils: JDBC工具类
 *
 * @author PonderYao
 * @since 1.0.0
 */
@Component("decisionJdbcUtils")
public class JdbcUtils {
    
    private static JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        JdbcUtils.jdbcTemplate = jdbcTemplate;
    }

    public static int update(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    public static <T> T select(String sql, Class<T> clazz, Object... args) {
        List<T> result = selectList(sql, clazz, args);
        return CollectionUtils.isEmpty(result) ? null : result.get(0);
    }
    
    public static <T> List<T> selectList(String sql, Class<T> clazz, Object... args) {
        List<T> result = jdbcTemplate.query(sql, args, new BeanPropertyRowMapper<>(clazz));
        return CollectionUtils.isEmpty(result) ? null : result;
    }
    
}
