package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class CommentBusinessException extends BaseBusinessException{
    public CommentBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
