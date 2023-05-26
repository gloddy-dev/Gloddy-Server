package com.gloddy.server.apply.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ApplyBusinessException;

public class NotFoundApplyException extends ApplyBusinessException {
    public NotFoundApplyException() {
        super(ErrorCode.APPLY_NOT_FOUND);
    }
}
