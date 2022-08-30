package com.gloddy.server.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetGroupResponse {

    @Getter
    @AllArgsConstructor
    public static class GetGroups {
        List<GetGroup> groups;
    }

    // TODO: 참가 멤버 수, 멤버 프로필
    @Getter
    @AllArgsConstructor
    public static class GetGroup {
        // String imageUrl;
        String title;
        String content;
        // List<String> memberProfiles;
        // int memberCount;
        String place;
        LocalDate meetDate;
    }
}
