package com.gloddy.server.comment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
