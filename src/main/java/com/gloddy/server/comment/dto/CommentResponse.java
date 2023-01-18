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

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class GetComment {
        //        private String userImageUrl;
        private String name;
        private String date;
        private String content;
    }
}
