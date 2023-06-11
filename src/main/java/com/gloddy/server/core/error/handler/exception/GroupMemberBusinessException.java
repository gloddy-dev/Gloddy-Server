package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class GroupMemberBusinessException extends BaseBusinessException{
    public GroupMemberBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
