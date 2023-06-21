package io.github.ponderyao.decision.extend;

import java.util.List;

import io.github.ponderyao.decision.entity.ActionEntry;
import io.github.ponderyao.decision.entity.ActionStub;
import io.github.ponderyao.decision.entity.ConditionEntry;
import io.github.ponderyao.decision.entity.ConditionStub;
import io.github.ponderyao.decision.entity.DecisionDomain;
import io.github.ponderyao.decision.entity.DecisionRule;

/**
 * DecisionManager: 决策管理接口
 * 
 * 除了组件自身实现必要的接口外, 还提供了一系列内置数据管理接口, 用于用户
 * 自主搭建决策管理平台的数据管理操作.
 *
 * @author PonderYao
 * @since 1.0.0
 */
public interface DecisionManager {

    DecisionDomain findDomain(String domainCode);

    List<DecisionRule> loadRules(DecisionDomain domain);

    List<ConditionStub> loadConditionStubList(DecisionDomain domain);

    List<ConditionEntry> loadConditionEntryList(ConditionStub conditionStub);

    List<ActionEntry> loadActionEntryList(DecisionRule rule);

    List<ActionStub> loadActionStubList(DecisionRule rule);

    /** 查询决策域列表 */
    List<DecisionDomain> findDomainList();

    /** 注册决策域 */
    void registerDomain(DecisionDomain domain);

    /** 修改决策域 */
    void modifyDomain(DecisionDomain domain);
    
    /** 删除决策域 */
    void removeDomain(DecisionDomain domain);
    
    /** 定义决策规则 */
    void defineRule(DecisionRule rule);
    
    /** 修改决策规则 */
    void modifyRule(DecisionRule rule);
    
    /** 删除决策规则 */
    void removeRule(DecisionRule rule);
    
    /** 定义条件 */
    void defineCondition(ConditionStub conditionStub);
    
    /** 修改条件 */
    void modifyCondition(ConditionStub conditionStub);
    
    /** 删除条件 */
    void removeCondition(ConditionStub conditionStub);
    
    /** 实现条件 */
    void realizeCondition(ConditionEntry conditionEntry);
    
    /** 修改条件项 */
    void modifyConditionEntry(ConditionEntry conditionEntry);
    
    /** 删除条件项 */
    void removeConditionEntry(ConditionEntry conditionEntry);
    
    /** 定义动作 */
    void defineAction(ActionStub actionStub);
    
    /** 修改动作 */
    void modifyAction(ActionStub actionStub);
    
    /** 删除动作 */
    void removeAction(ActionStub actionStub);
    
    /** 编排动作 */
    void arrangeAction(ActionEntry actionEntry);
    
    /** 修改动作项 */
    void modifyActionEntry(ActionEntry actionEntry);

    /** 删除动作项 */
    void removeActionEntry(ActionEntry actionEntry);
    
}
