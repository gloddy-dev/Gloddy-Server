package com.gloddy.server.comment.api;

import com.gloddy.server.comment.dto.CommentRequest;
import com.gloddy.server.comment.dto.CommentResponse;
import com.gloddy.server.comment.service.CommentService;
import com.gloddy.server.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{articleId}/comment")
    public ResponseEntity<CommentResponse.Create> create(
            @PathVariable Long articleId,
            @RequestBody @Valid CommentRequest.Create request,
            @AuthenticationPrincipal Long userId
    ) {
        CommentResponse.Create response = commentService.create(userId, articleId, request);
        return ApiResponse.created(response);
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long articleId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal Long userId
    ) {
        commentService.delete(commentId, userId, articleId);
        return ApiResponse.noContent();
    }
}
