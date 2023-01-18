package com.gloddy.server.article.handler;

import com.gloddy.server.article.entity.Article;
import com.gloddy.server.article.repository.ArticleJpaRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ArticleBusinessException;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
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
