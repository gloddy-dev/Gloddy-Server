package com.gloddy.server.article.application;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.dto.ArticlePayload;
import com.gloddy.server.article.domain.dto.in.GroupArticlePayloadEventType;
import com.gloddy.server.article.domain.handler.ArticleQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticlePayloadService {
    private final ArticleQueryHandler articleQueryHandler;

    public ArticlePayload getArticlePayload(Long articleId, GroupArticlePayloadEventType eventType) {
        Article article = articleQueryHandler.findByIdFetchUserAndGroup(articleId);

        return ArticlePayload.toDto(article);
    }
}
