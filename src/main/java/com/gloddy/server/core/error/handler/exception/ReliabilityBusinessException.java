package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class ReliabilityBusinessException extends BaseBusinessException {
    public ReliabilityBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
