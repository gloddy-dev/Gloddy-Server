package com.gloddy.server.article.domain.dto;
import com.gloddy.server.core.response.PageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ArticleResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
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
    public static class GetPreview {
        private String groupFileUrl;
        private String groupTitle;
        private String groupContent;
        PageResponse<GetArticle> previews;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class GetArticle {
        private String userImageUrl;
        private String name;
        private String date;
        private String content;
        private boolean notice;
        private int commentCount;
        private List<ImageDto> images;
    }
}
