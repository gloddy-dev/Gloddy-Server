package com.gloddy.server.article.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class ArticleRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Create {
        @NotBlank
        private String content;
        private boolean notice;
        private List<String> images;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Update {
        @NotBlank
        private String content;
        private boolean notice;
        private List<String> images;
    }
}