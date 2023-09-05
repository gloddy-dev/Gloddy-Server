package com.gloddy.server.group.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.GroupBusinessException;

public class GroupTimeInvalidException extends GroupBusinessException {
    public GroupTimeInvalidException() {
        super(ErrorCode.GROUP_TIME_INVALID);
    }
}
