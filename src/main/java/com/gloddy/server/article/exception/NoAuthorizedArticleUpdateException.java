package com.gloddy.server.article.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ArticleBusinessException;

public class NoAuthorizedArticleUpdateException extends ArticleBusinessException {
    public NoAuthorizedArticleUpdateException() {
        super(ErrorCode.NO_ARTICLE_WRITER);
    }
}
