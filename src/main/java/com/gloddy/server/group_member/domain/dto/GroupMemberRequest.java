package com.gloddy.server.group_member.domain.dto;

import com.gloddy.server.praise.domain.vo.PraiseValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class GroupMemberRequest {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Estimate {
        private List<PraiseInfo> praiseInfos;
        private MateInfo mateInfo;

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class PraiseInfo {
            private Long userId;
            private PraiseValue praiseValue;
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        @Setter
        public static class MateInfo {
            private Long userId;
            private String selectionReason;
        }
    }

}
