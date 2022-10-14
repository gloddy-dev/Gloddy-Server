package com.gloddy.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CrateMate {
        Long mateId;
    }
}
