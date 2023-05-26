package com.gloddy.server.article.domain.handler.impl;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.handler.ArticleCommandHandler;
import com.gloddy.server.article.infra.repository.ArticleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleCommandHandlerImpl implements ArticleCommandHandler {

    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public Article save(Article article) {
        return articleJpaRepository.save(article);
    }

    @Override
    public void delete(Article article) {
        articleJpaRepository.delete(article);
    }
}
