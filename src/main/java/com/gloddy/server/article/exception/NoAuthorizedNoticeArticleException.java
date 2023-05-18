package com.gloddy.server.article.exception;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ArticleBusinessException;

public class NoAuthorizedNoticeArticleException extends ArticleBusinessException {
    public NoAuthorizedNoticeArticleException() {
        super(ErrorCode.NO_NOTICE_ARTICLE_PERMISSION);
    }
}
