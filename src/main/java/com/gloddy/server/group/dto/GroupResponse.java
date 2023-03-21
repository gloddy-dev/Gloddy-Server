package com.gloddy.server.group.dto;

import com.gloddy.server.core.dto.UserInfoDto;
import com.gloddy.server.group.entity.Group;
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
        Long groupId;
        String imageUrl;
        String title;
        String content;
        // List<String> memberProfiles;
        int memberCount;
        String place;
        LocalDate meetDate;

        public static GetGroup from(Group group) {
            return new GetGroup(
                    group.getId(),
                    group.getFileUrl(),
                    group.getTitle(),
                    group.getContent(),
                    group.getMemberCount(),
                    group.getPlace(),
                    group.getMeetDate()
            );
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetGroupDetail {
        private Boolean myGroup;
        private Boolean isCaptain;
        private String title;
        private String fileUrl;
        private String content;
        private int CountParticipants;
        private List<UserInfoDto> userInfoDtos;
        private String meetDate;
        private String startTime;
        private String endTime;
        private String place;
        private String place_latitude;
        private String place_longitude;
    }

}
