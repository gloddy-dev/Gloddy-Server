package com.gloddy.server.comment.api;

import com.gloddy.server.comment.domain.dto.CommentRequest;
import com.gloddy.server.comment.application.CommentService;
import com.gloddy.server.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.gloddy.server.comment.domain.dto.CommentResponse.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups/{groupId}/articles/{articleId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<Create> create(
            @PathVariable("groupId") Long groupId,
            @PathVariable("articleId") Long articleId,
            @RequestBody @Valid CommentRequest.Create request,
            @AuthenticationPrincipal Long userId
    ) {
        Create response = commentService.create(userId, articleId, request);
        return ApiResponse.created(response);
    }

    @GetMapping("/{articleId}/comments")
    public ResponseEntity<GetComments> getAll(
            @PathVariable("groupId") Long groupId,
            @PathVariable("articleId") Long articleId,
            @AuthenticationPrincipal Long userId
    ) {
        GetComments response = commentService.getComments(articleId, userId);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable("groupId") Long groupId,
            @PathVariable("articleId") Long articleId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal Long userId
    ) {
        commentService.delete(groupId, articleId, commentId, userId);
        return ApiResponse.noContent();
    }
}
