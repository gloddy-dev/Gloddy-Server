package com.gloddy.server.group.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        private String endTime;
        private String placeName;
        private String placeAddress;
        private String placeLatitude;
        private String placeLongitude;
        private int maxUser;
    }

}
