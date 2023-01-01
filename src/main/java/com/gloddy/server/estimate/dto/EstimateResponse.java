package com.gloddy.server.estimate.dto;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.auth.entity.User;
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
                return new GetGroupMember(user.getImageUrl(), user.getName());
            }
        }
    }
}
