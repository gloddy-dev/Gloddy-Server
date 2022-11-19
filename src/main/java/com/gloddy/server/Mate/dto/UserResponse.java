package com.gloddy.server.Mate.dto;

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
