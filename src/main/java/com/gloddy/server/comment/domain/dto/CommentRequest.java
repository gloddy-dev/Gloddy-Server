package com.gloddy.server.comment.domain.dto;

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
    public static class Create {
        @NotBlank
        private String content;
    }
}
