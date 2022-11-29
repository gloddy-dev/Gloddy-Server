package com.gloddy.server.article.api;

import com.gloddy.server.article.dto.ArticleRequest;
import com.gloddy.server.article.dto.ArticleResponse;
import com.gloddy.server.article.service.ArticleService;
import com.gloddy.server.core.response.ApiResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.gloddy.server.article.dto.ArticleResponse.*;

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
    public ResponseEntity<Update> update(
            @PathVariable Long articleId,
            @RequestBody @Valid ArticleRequest.Update request,
            @AuthenticationPrincipal Long userId
    ) {
        Update response = articleService.update(articleId, userId, request);
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

    @GetMapping("/groups/{groupId}/articles")
    public ResponseEntity<GetPreview> getPreview(
        @PathVariable Long groupId,
        @AuthenticationPrincipal Long userId
    ) {
        GetPreview response = articleService.getPreview(groupId, userId);
        return ApiResponse.ok(response);
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<GetDetail> getDetail(
        @PathVariable Long articleId,
        @AuthenticationPrincipal Long userId
    ) {
        GetDetail response = articleService.getDetail(articleId, userId);
        return ApiResponse.ok(response);
    }
}
