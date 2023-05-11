package com.gloddy.server.apply.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

public class ApplyRequest {

    @Getter
    @AllArgsConstructor
    public static class create{
        @NotBlank(message = "introduce is empty")
        private String introduce;
        @NotBlank(message = "reason is empty")
        private String reason;
    }
}
