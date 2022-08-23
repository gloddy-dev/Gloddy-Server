package com.gloddy.server.group.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class GroupResponse {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {

        private Long groupId;
    }

}
