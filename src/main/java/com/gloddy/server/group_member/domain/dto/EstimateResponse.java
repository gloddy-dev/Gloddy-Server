package com.gloddy.server.group_member.domain.dto;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Profile;
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
            private String imageUrl;
            private String name;

            public static GetGroupMember from(User user) {
                Profile profile = user.getProfile();
                return new GetGroupMember(profile.getImageUrl(), profile.getNickname());
            }
        }
    }
}
