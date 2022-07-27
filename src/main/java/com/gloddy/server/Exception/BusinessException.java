package com.gloddy.server.Exception;

public class BusinessException extends RuntimeException{
    private final ExceptionCode exceptionCode;

    public BusinessException(String message, ExceptionCode exceptionCode) {
        super(message);
        this.exceptionCode = exceptionCode;
    }

    public BusinessException(ExceptionCode exceptionCode) {
        super(exceptionCode.getDescription());
        this.exceptionCode = exceptionCode;
    }
}
