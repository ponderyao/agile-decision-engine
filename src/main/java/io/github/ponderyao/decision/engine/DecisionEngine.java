package io.github.ponderyao.decision.engine;

import io.github.ponderyao.decision.value.DecisionCondition;
import io.github.ponderyao.decision.value.DecisionResult;

/**
 * DecisionEngine: 决策引擎 Marker
 * 
 * @author PonderYao
 * @since 1.0.0
 */
public interface DecisionEngine {

    /**
     * 执行引擎
     * 
     * @param condition 决策条件
     * @return 决策执行结果
     */
    DecisionResult execute(DecisionCondition condition);
    
}
