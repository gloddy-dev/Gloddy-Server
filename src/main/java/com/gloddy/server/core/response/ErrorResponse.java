package com.gloddy.server.core.response;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String reason;

    public ErrorResponse(ErrorCode errorCode) {
        this.timestamp = LocalDateTime.now();
        this.status = errorCode.getStatus();
        this.message = errorCode.name();
        this.reason = errorCode.getErrorMessage();
    }
}
