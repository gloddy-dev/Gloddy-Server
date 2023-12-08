package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class BatchBusinessException extends BaseBusinessException {
    public BatchBusinessException(ErrorCode errorCode, Exception e) {
        super(errorCode, e);
    }
}
