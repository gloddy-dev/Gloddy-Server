package com.gloddy.server.group.domain.dto;

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
    public static class Create {
        private String imageUrl;
        private String title;
        private String content;
        private LocalDate meetDate;
        private String startTime;
        private String endTime;
        private String place;
        private String place_latitude;
        private String place_longitude;
        private int maxUser;
    }

}
