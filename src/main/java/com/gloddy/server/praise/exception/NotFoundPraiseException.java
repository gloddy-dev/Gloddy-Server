package com.gloddy.server.praise.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.PraiseBusinessException;

public class NotFoundPraiseException extends PraiseBusinessException {
    public NotFoundPraiseException() {
        super(ErrorCode.PRAISE_NOT_FOUND);
    }
}
