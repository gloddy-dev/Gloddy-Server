package com.gloddy.server.apply.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyResponse {

    @Getter
    @AllArgsConstructor
    public static class Create {
        private Long applyId;
    }
}
