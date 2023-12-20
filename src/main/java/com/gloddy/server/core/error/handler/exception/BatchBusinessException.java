package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class BatchBusinessException extends BaseBusinessException {
    public BatchBusinessException(Exception e) {
        super(ErrorCode.GROUP_NOTIFICATION_BATCH_ERROR, e);
    }
}
