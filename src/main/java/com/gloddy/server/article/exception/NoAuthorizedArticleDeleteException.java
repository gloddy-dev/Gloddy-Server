package com.gloddy.server.article.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ArticleBusinessException;

public class NoAuthorizedArticleDeleteException extends ArticleBusinessException {
    public NoAuthorizedArticleDeleteException() {
        super(ErrorCode.NO_ARTICLE_DELETE_PERMISSION);
    }
}
