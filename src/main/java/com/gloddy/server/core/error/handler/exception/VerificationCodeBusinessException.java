package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class VerificationCodeBusinessException extends BaseBusinessException{
    public VerificationCodeBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
