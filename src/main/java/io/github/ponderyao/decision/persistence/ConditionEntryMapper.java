package io.github.ponderyao.decision.persistence;

import java.util.List;

import io.github.ponderyao.decision.entity.ConditionEntry;

/**
 * ConditionEntryMapper: 条件项 数据访问接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface ConditionEntryMapper extends BaseMapper<ConditionEntry> {
    
    List<ConditionEntry> selectListByCondition(Long domainId, Long conditionStubId);
    
    ConditionEntry selectByRuleAndCondition(Long domainId, Long ruleId, Long conditionStubId);
    
}
