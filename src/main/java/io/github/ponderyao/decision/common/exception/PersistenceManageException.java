package io.github.ponderyao.decision.common.exception;

import io.github.ponderyao.decision.common.constant.ExceptionConstants;

/**
 * PersistenceManageException: 持久化管理异常
 *
 * @author PonderYao
 * @since 1.0.0
 */
public class PersistenceManageException extends RuntimeException {
    
    private static final long serialVersionUID = -2599937327861459713L;
    
    public PersistenceManageException(String errorConst, Object... args) {
        super(ExceptionConstants.HEADER.MANAGE_PERSISTENCE + String.format(errorConst, args));
    }
    
}
