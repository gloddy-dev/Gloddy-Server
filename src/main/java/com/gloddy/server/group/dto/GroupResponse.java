package com.gloddy.server.group.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GroupResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {
        private Long groupId;
    }

    @Getter
    @AllArgsConstructor
    public static class GetGroups {
        List<GroupResponse.GetGroup> groups;
    }

    // TODO: 참가 멤버 수, 멤버 프로필
    @Getter
    @AllArgsConstructor
    public static class GetGroup {
        // String imageUrl;
        String title;
        String content;
        // List<String> memberProfiles;
        int memberCount;
        String place;
        LocalDate meetDate;
    }

}
