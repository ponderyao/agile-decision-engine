package io.github.ponderyao.decision.common.exception;

import io.github.ponderyao.decision.common.constant.ExceptionConstants;

/**
 * DecisionEngineException: 决策引擎执行异常
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class DecisionEngineException extends RuntimeException {
    
    private static final long serialVersionUID = 910616462516810810L;
    
    public DecisionEngineException(String errorConst, Object... args) {
        super(ExceptionConstants.HEADER.ENGINE_DECISION + String.format(errorConst, args));
    }
    
}
