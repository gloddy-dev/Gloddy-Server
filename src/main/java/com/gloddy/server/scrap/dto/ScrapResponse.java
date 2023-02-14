package com.gloddy.server.scrap.dto;

import com.gloddy.server.group.dto.GroupResponse.GetGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class ScrapResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CreateScrap {
        private Long scrapId;
    }
}
