package com.gloddy.server.apply.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ApplyBusinessException;

public class AlreadyProcessedApplyException extends ApplyBusinessException {

    public AlreadyProcessedApplyException() {
        super(ErrorCode.AlREADY_PROCESSED_APPLY);
    }
}
