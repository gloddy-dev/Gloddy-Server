package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class SmsUnauthorizedException extends BaseBusinessException {
    public SmsUnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
