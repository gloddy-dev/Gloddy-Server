package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class GroupBusinessException extends BaseBusinessException{
    public GroupBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
