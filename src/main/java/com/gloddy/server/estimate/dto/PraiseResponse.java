package com.gloddy.server.estimate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PraiseResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getPraiseForUser {
        private int totalCalmCount;
        private int totalKindCount;
        private int totalActiveCount;
        private int totalHumorCount;
        private int totalAbsenceCount;
    }
}
