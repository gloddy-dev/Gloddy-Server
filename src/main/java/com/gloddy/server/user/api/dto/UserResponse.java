package com.gloddy.server.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ExistNickname {
        private Boolean isExistNickname;
    }
}
