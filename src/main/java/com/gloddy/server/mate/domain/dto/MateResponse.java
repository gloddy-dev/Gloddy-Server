package com.gloddy.server.mate.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MateResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getMatesForUser {
        List<getMateForUser> mates;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getMateForUser {
        private Long mateId;
        private String mateImageUrl;
        private String mateName;
        private String school;
        private LocalDateTime createdAt;
        private String selectionReason;
    }
}
