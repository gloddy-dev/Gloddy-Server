package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class EventBusinessException extends BaseBusinessException{
    public EventBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
