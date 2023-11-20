package com.gloddy.server.group_member.api.dto;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class GroupMemberResponse {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class GetAll {
        private List<GetOne> groupMembers;

        public static GetAll of(List<GetOne> groupMembers) {
            return new GetAll(groupMembers);
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class GetOne {
        private Boolean isCaptain;
        private Boolean isCertifiedStudent;
        private Long userId;
        private String nickName;
        private String imageUrl;
        private ReliabilityLevel reliabilityLevel;

        public static GetOne from(GroupMember groupMember) {
            User user = groupMember.getUser();

            return new GetOne(
                    groupMember.isCaptain(),
                    user.isCertifiedStudent(),
                    user.getId(),
                    user.getNickName(),
                    user.getImageUrl(),
                    user.getReliability().getLevel()
            );
        }
    }
}
