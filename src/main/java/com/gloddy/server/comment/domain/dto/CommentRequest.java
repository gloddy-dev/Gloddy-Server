package com.gloddy.server.comment.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CommentRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Schema(name = "CommentCreateRequest")
    public static class Create {
        @NotBlank
        private String content;
    }
}
