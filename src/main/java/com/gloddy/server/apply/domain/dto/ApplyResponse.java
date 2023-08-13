package com.gloddy.server.apply.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyResponse {

    @Getter
    @AllArgsConstructor
    @Schema(name = "ApplyCreateResponse")
    public static class Create {
        private Long applyId;
    }
}
