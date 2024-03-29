package com.gloddy.server.article.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class ArticleRequest {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Schema(name = "ArticleCreateRequest")
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
