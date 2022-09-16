package com.gloddy.server.article.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
