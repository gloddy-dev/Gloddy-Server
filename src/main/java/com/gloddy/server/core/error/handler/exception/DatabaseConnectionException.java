package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class DatabaseConnectionException extends BaseBusinessException {
    public DatabaseConnectionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
