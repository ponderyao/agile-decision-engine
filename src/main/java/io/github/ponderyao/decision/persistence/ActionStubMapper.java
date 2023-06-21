package io.github.ponderyao.decision.persistence;

import java.util.List;

import io.github.ponderyao.decision.entity.ActionStub;

/**
 * ActionStubMapper: 动作桩 数据访问接口
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface ActionStubMapper extends BaseMapper<ActionStub> {
    
    List<ActionStub> selectListByRule(Long domainId, Long ruleId);
    
    ActionStub selectByCode(Long domainId, String actionCode);
    
}
