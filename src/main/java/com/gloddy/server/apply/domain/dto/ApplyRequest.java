package com.gloddy.server.apply.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


public class ApplyRequest {

    @Getter
    @AllArgsConstructor
    public static class Create{
        @NotBlank(message = "introduce is empty")
        private String introduce;
        @NotBlank(message = "reason is empty")
        private String reason;
    }
}
