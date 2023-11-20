package com.gloddy.server.praise.domain.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PraiseResponse {

    @Getter
    @NoArgsConstructor
    public static class GetPraiseForUser {
        private int totalCalmCount;
        private int totalKindCount;
        private int totalActiveCount;
        private int totalHumorCount;
        private int totalAbsenceCount;

        @QueryProjection
        public GetPraiseForUser(int totalCalmCount, int totalKindCount, int totalActiveCount,
                                int totalHumorCount, int totalAbsenceCount) {
            this.totalCalmCount = totalCalmCount;
            this.totalKindCount = totalKindCount;
            this.totalActiveCount = totalActiveCount;
            this.totalHumorCount = totalHumorCount;
            this.totalAbsenceCount = totalAbsenceCount;
        }
    }
}
