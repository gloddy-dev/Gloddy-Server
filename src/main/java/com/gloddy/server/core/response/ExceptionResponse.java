package com.gloddy.server.core.response;

import com.gloddy.server.Exception.ExceptionCode;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final String reason;

    public ExceptionResponse(ExceptionCode errorCode) {
        this.timestamp = LocalDateTime.now();
        this.status = errorCode.getStatus().value();
        this.message = errorCode.name();
        this.reason = errorCode.getDescription();
    }
}
