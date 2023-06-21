package io.github.ponderyao.decision.common.exception;

import io.github.ponderyao.decision.common.constant.ExceptionConstants;

/**
 * ScriptEngineException: 脚本引擎执行异常
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class ScriptEngineException extends RuntimeException {
    
    private static final long serialVersionUID = 2001371827220326387L;
    
    public ScriptEngineException(String scriptName, String errorConst, Object... args) {
        super(String.format(ExceptionConstants.HEADER.ENGINE_SCRIPT, scriptName) + String.format(errorConst, args));
    }
    
}
