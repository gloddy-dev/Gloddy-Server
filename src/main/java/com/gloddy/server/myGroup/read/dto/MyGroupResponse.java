package com.gloddy.server.myGroup.read.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class MyGroupResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
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
    public static class Participating {

        private List<One> groups;

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class One {
            private Boolean isNew;
            private GroupInfo group;
        }
    }
}
