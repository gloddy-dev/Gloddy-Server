package com.gloddy.server.core.response;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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

    private ErrorResponse(int status, String message, String reason){
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.reason = reason;
    }

    public static ErrorResponse of(int status, String message, String reason) {
        return new ErrorResponse(status, message, reason);
    }
}
