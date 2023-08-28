package com.gloddy.server.apply.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ApplyBusinessException;

public class UnAuthorizedApplyGetException extends ApplyBusinessException {
    public UnAuthorizedApplyGetException() {
        super(ErrorCode.GROUP_NOT_CAPTAIN);
    }
}
