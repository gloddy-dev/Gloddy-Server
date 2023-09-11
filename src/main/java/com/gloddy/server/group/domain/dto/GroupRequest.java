package com.gloddy.server.group.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GroupRequest {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Schema(name = "GroupCreateRequest")
    public static class Create {
        private String imageUrl;
        private String title;
        private String content;
        private LocalDate meetDate;
        private String startTime;
        private String placeName;
        private String placeAddress;
        private String placeId;
        private BigDecimal placeLatitude;
        private BigDecimal placeLongitude;
        private int maxUser;
    }

}
