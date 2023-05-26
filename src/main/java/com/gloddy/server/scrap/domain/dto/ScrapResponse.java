package com.gloddy.server.scrap.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ScrapResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CreateScrap {
        private Long scrapId;
    }
}
