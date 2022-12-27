package com.gloddy.server.core.error.handler.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;

public class ArticleBusinessException extends BaseBusinessException {
    public ArticleBusinessException(ErrorCode errorCode) {
        super(errorCode);
    }
}
