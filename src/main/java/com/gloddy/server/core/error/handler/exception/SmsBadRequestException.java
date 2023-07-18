package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class SmsBadRequestException extends BaseBusinessException {
    public SmsBadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
