package com.gloddy.server.article.handler;

import com.gloddy.server.article.entity.Article;

public interface ArticleHandler {
    Article findById(Long id);
}
