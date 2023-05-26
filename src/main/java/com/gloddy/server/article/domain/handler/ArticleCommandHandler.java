package com.gloddy.server.article.domain.handler;

import com.gloddy.server.article.domain.Article;

public interface ArticleCommandHandler {

    Article save(Article article);

    void delete(Article article);
}
