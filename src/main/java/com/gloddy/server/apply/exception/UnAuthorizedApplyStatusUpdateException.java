package com.gloddy.server.apply.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ApplyBusinessException;

public class UnAuthorizedApplyStatusUpdateException extends ApplyBusinessException {
    public UnAuthorizedApplyStatusUpdateException() {
        super(ErrorCode.GROUP_NOT_CAPTAIN);
    }
}
