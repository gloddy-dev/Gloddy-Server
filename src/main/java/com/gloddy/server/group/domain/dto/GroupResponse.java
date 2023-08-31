package com.gloddy.server.group.domain.dto;

import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.gloddy.server.core.utils.DateTimeUtils.*;

public class GroupResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(name = "GroupCreateResponse")
    public static class Create {
        private Long groupId;
    }

    @Getter
    @AllArgsConstructor
    public static class GetGroups {
        List<GroupResponse.GetGroup> groups;
    }

    @Getter
    @AllArgsConstructor
    public static class GetGroup {
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

        public static GetGroup from(Group group) {
            return new GetGroup(
                    group.getId(),
                    group.getImageUrl(),
                    group.getTitle(),
                    group.getContent(),
                    group.getMemberCount(),
                    group.getMaxUser(),
                    group.getPlace().getName(),
                    group.getPlace().getAddress(),
                    group.getMeetDate().toString(),
                    group.getStartDateTime().toLocalTime().toString(),
                    group.getEndDateTime().toLocalTime().toString()
            );
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetGroupDetail {
        private Boolean myGroup;
        private Boolean isCaptain;
        private Long groupId;
        private String title;
        private String fileUrl;
        private String content;
        private int maxMemberCount;
        private int memberCount;
        private String meetDate;
        private String startTime;
        private String endTime;
        private String placeName;
        private String placeAddress;
        private String placeLatitude;
        private String placeLongitude;
        private Boolean isScraped;
        private Status applyStatus;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetParticipatedGroup {
        private Long groupId;
        private String imageUrl;
        private String title;
        private String content;
        private int memberCount;
        private String place;
        private LocalDate meetDate;
        private boolean isPraised;

        public static GetParticipatedGroup from(GroupMember groupMember) {
            return new GetParticipatedGroup(
                    groupMember.getGroup().getId(),
                    groupMember.getGroup().getImageUrl(),
                    groupMember.getGroup().getTitle(),
                    groupMember.getGroup().getContent(),
                    groupMember.getGroup().getMemberCount(),
                    groupMember.getGroup().getPlace().getName(),
                    groupMember.getGroup().getMeetDate(),
                    groupMember.isEndEstimate()
            );
        }
    }

}
