package com.gloddy.server.article.domain.handler;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.infra.repository.ArticleJpaRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ArticleBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleHandlerImpl implements ArticleHandler {

    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public Article findById(Long id) {
        return articleJpaRepository.findById(id)
                .orElseThrow(() -> new ArticleBusinessException(ErrorCode.ARTICLE_NOT_FOUND));
    }
}
