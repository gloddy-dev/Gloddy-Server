package com.gloddy.server.article.api;

import com.gloddy.server.article.dto.ArticleRequest;
import com.gloddy.server.article.dto.ArticleResponse;
import com.gloddy.server.article.service.ArticleService;
import com.gloddy.server.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/groups/{groupId}/article")
    public ResponseEntity<ArticleResponse.Create> create(
            @PathVariable Long groupId,
            @RequestBody ArticleRequest.Create request,
            @AuthenticationPrincipal Long userId
    ) {
        ArticleResponse.Create response = articleService.create(groupId, userId, request);
        return ApiResponse.created(response);
    }

    @PatchMapping("/articles/{articleId}")
    public ResponseEntity<ArticleResponse.Update> update(
            @PathVariable Long articleId,
            @RequestBody ArticleRequest.Update request,
            @AuthenticationPrincipal Long userId
    ) {
        ArticleResponse.Update response = articleService.update(articleId, userId, request);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long articleId,
            @AuthenticationPrincipal Long userId
    ) {
        articleService.delete(articleId, userId);
        return ApiResponse.noContent();
    }
}
