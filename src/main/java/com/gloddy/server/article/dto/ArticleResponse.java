package com.gloddy.server.article.dto;

import com.gloddy.server.core.response.PageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        private boolean isNotice;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class GetPreview {
        private String groupFileUrl;
        private String groupTitle;
        private String groupContent;
        PageResponse<GetArticle> previews;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class GetArticle {
//        private String userImageUrl;
        private String name;
        private String date;
        private String content;
        private int commentCount;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class GetDetail {
//        private String userImageUrl;
        private GetArticle article;
        private List<GetComment> comments;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class GetComment {
//        private String userImageUrl;
        private String name;
        private String date;
        private String content;
    }
}
