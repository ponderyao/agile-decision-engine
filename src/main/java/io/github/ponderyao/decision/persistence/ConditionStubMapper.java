package io.github.ponderyao.decision.persistence;

import java.util.List;

import io.github.ponderyao.decision.entity.ConditionStub;

/**
 * ConditionStubMapper: 条件桩 数据操作接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface ConditionStubMapper extends BaseMapper<ConditionStub> {
    
    List<ConditionStub> selectListByDomain(Long domainId);
    
    ConditionStub selectByCode(Long domainId, String conditionCode);
    
}
