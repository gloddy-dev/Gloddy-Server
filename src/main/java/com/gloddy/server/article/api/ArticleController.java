package com.gloddy.server.article.api;

import com.gloddy.server.article.domain.dto.ArticleRequest;
import com.gloddy.server.article.application.ArticleService;
import com.gloddy.server.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.gloddy.server.article.domain.dto.ArticleResponse.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("/groups/{groupId}/article")
    public ResponseEntity<Create> create(
            @PathVariable Long groupId,
            @RequestBody @Valid ArticleRequest.Create request,
            @AuthenticationPrincipal Long userId
    ) {
        Create response = articleService.create(groupId, userId, request);
        return ApiResponse.created(response);
    }

    @PatchMapping("/articles/{articleId}")
    public ResponseEntity<Void> update(
            @PathVariable Long articleId,
            @RequestBody @Valid ArticleRequest.Update request,
            @AuthenticationPrincipal Long userId
    ) {
        articleService.update(articleId, userId, request);
        return ApiResponse.noContent();
    }

    @DeleteMapping("/groups/{groupId}/articles/{articleId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long groupId,
            @PathVariable Long articleId,
            @AuthenticationPrincipal Long userId
    ) {
        articleService.delete(groupId, articleId, userId);
        return ApiResponse.noContent();
    }

    @GetMapping("/groups/{groupId}/articles")
    public ResponseEntity<GetPreview> getPreview(
        @PathVariable Long groupId,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        GetPreview response = articleService.getPreview(groupId, page, size);
        return ApiResponse.ok(response);
    }
}
