package io.github.ponderyao.decision.persistence;

import java.util.List;

import io.github.ponderyao.decision.entity.BaseEntity;

/**
 * BaseMapper
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface BaseMapper<T extends BaseEntity> {
    
    void insert(T entity);
    
    T selectById(Long id);
    
    List<T> selectList();
    
    void update(T entity);
    
    void delete(T entity);
    
}
