package com.gloddy.server.group_member.domain.dto;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Profile;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class EstimateResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetGroupMembers{
        private List<GetGroupMember> groupMemberList;

        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class GetGroupMember{
            private Long userId;
            private Boolean isCaptain;
            private Boolean isCertifiedStudent;
            private String imageUrl;
            private String nickName;
            private ReliabilityLevel reliabilityLevel;

            public static GetGroupMember from(User user, Group group) {
                Profile profile = user.getProfile();
                return new GetGroupMember(
                        user.getId(),
                        group.isCaptain(user),
                        user.isCertifiedStudent(),
                        profile.getImageUrl(),
                        profile.getNickname(),
                        user.getReliabilityLevel());
            }
        }
    }
}
