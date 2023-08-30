package com.gloddy.server.myGroup.read.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MyGroupResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "MyGroupGetGroupInfoResponse")
    public static class GroupInfo {
        private Long groupId;
        private String imageUrl;
        private String title;
        private String content;
        private int memberCount;
        private int maxMemberCount;
        private String placeName;
        private String placeAddress;
        private String meetDate;
        private String startTime;
        private String endTime;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "MyGroupGetParticipatingResponse")
    public static class Participating {

        private List<One> groups;

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        @Schema(name = "MyGroupGetParticipatingOneResponse")
        public static class One {
            private Boolean isNew;
            private GroupInfo group;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "MyGroupGetHostingResponse")
    public static class Hosting {
        private List<One> groups;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Schema(name = "MyGroupGetHostingOneResponse")
        public static class One {
            private Boolean isExistNewApply;
            private GroupInfo group;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "MyGroupGetWaitingResponse")
    public static class Waiting {
        private List<One> groups;
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Schema(name = "MyGroupGetWaitingOneResponse")
        public static class One {
            private GroupInfo group;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "MyGroupGetRejectedResponse")
    public static class Rejected {
        private List<One> groups;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Schema(name = "MyGroupGetRejectedOneResponse")
        public static class One {
            private Long applyId;
            private GroupInfo group;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "MyGroupGetNotEstimatedResponse")
    public static class NotEstimated {
        private List<One> groups;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Schema(name = "MyGroupGetNotEstimatedOneResponse")
        public static class One {
            private Boolean isCaptain;
            private GroupInfo group;
        }
    }
}
