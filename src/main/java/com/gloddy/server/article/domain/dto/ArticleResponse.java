package com.gloddy.server.article.domain.dto;
import com.gloddy.server.core.response.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ArticleResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "ArticleCreateResponse")
    public static class Create {
        private Long articleId;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private String content;
        private boolean notice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class GetArticle {
        private Long articleId;
        private String userImageUrl;
        private String name;
        private String date;
        private String content;
        private boolean notice;
        private int commentCount;
        private Boolean isWriter;
        private Boolean isWriterCaptain;
        private Boolean isWriterCertifiedStudent;
        private String writerReliabilityLevel;
        private List<String> images;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class GetNotice {
        private Long noticeId;
        private String content;
    }
}
