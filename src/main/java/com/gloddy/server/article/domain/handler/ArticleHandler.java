package com.gloddy.server.article.domain.handler;

import com.gloddy.server.article.domain.Article;

public interface ArticleHandler {
    Article findById(Long id);
}
