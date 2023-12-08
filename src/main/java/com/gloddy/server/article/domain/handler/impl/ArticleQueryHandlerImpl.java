package com.gloddy.server.article.domain.handler.impl;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.handler.ArticleQueryHandler;
import com.gloddy.server.article.infra.repository.ArticleJpaRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ArticleBusinessException;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleQueryHandlerImpl implements ArticleQueryHandler {

    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public Article findById(Long id) {
        return articleJpaRepository.findById(id)
                .orElseThrow(() -> new ArticleBusinessException(ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public Article findByIdFetchUserAndGroup(Long id) {
        return articleJpaRepository.findByIdFetchUserAndGroup(id)
                .orElseThrow(() -> new ArticleBusinessException(ErrorCode.ARTICLE_NOT_FOUND));
    }

    @Override
    public Page<Article> findAllToGetArticlePreview(Group group, Pageable pageable) {
        return articleJpaRepository.findAllByGroupFetchUserAndGroup(group, pageable);
    }

    @Override
    public List<Article> findAllByGroupAndNotice(Group group, boolean isNotice) {
        return articleJpaRepository.findAllByGroupAndNotice(group, isNotice);
    }
}
