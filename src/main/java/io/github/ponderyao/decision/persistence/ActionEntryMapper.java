package io.github.ponderyao.decision.persistence;

import java.util.List;

import io.github.ponderyao.decision.entity.ActionEntry;

/**
 * ActionEntryMapper: 动作项 数据访问接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface ActionEntryMapper extends BaseMapper<ActionEntry> {
    
    List<ActionEntry> selectListByRule(Long domainId, Long ruleId);
    
}
