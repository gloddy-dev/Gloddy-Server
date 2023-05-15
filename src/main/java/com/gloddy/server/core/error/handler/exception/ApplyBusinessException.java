package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class ApplyBusinessException extends BaseBusinessException{
    public ApplyBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
