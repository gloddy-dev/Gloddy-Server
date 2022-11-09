package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import lombok.Getter;

@Getter
public class BaseBusinessException extends RuntimeException{

    private final ErrorCode errorCode;

    public BaseBusinessException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
