package com.gloddy.server.article.api.in;

import com.gloddy.server.article.application.ArticlePayloadService;
import com.gloddy.server.article.domain.dto.ArticlePayload;
import com.gloddy.server.core.response.ApiResponse;
import com.gloddy.server.messaging.adapter.group.event.GroupArticleEventType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/payload")
public class ArticlePayloadApi {
    private final ArticlePayloadService articlePayloadService;

    @GetMapping("/groups/articles/{articleId}")
    public ResponseEntity<ArticlePayload> getArticlePayload(
            @PathVariable("articleId") Long articleId,
            @RequestParam("eventType") GroupArticleEventType eventType
    ) {
        ArticlePayload response = articlePayloadService.getArticlePayload(articleId, eventType);
        return ApiResponse.ok(response);
    }
}
