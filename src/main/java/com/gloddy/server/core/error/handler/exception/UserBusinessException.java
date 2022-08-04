package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorcode.ErrorCode;

public class UserBusinessException extends RuntimeException{
    private final ErrorCode errorCode;

    public UserBusinessException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
