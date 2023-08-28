package com.gloddy.server.comment.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CommentResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Schema(name = "CommentCreateResponse")
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
        private Boolean isWriter;
        private Boolean isWriterCaptain;
        private Boolean isWriterCertifiedStudent;
        private String writerReliabilityLevel;
    }
}
