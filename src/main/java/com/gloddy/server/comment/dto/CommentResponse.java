package com.gloddy.server.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class Create {
        Long commentId;
    }
}
