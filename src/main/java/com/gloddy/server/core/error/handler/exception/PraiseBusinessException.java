package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class PraiseBusinessException extends BaseBusinessException {
    public PraiseBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
