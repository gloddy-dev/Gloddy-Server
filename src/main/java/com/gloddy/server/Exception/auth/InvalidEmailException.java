package com.gloddy.server.Exception.auth;

import com.gloddy.server.Exception.BusinessException;
import com.gloddy.server.Exception.ExceptionCode;

public class InvalidEmailException extends BusinessException {

    public InvalidEmailException() {
        super(ExceptionCode.INVALID_EMAIL);
    }
}
