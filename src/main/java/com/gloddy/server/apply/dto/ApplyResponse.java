package com.gloddy.server.apply.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyResponse {

    @Getter
    @AllArgsConstructor
    public static class create {
        private Long applyId;
    }
}
