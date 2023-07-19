package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class SmsBusinessException extends BaseBusinessException{
    public SmsBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
