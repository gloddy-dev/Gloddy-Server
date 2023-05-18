package com.gloddy.server.article.domain.handler;

import com.gloddy.server.article.domain.Article;

public interface ArticleQueryHandler {
    Article findById(Long id);
}
