package com.gloddy.server.comment.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    public static class GetComments {
        private List<GetComment> comments;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class GetComment {
        private Long commentId;
        private String userImageUrl;
        private String name;
        private String date;
        private String content;
        private boolean isWriter;
    }
}
