package com.gloddy.server.authEmail.domain.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthEmailResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "AuthEmailVerifyCodeResponse")
    public static class VerifyCode {
        private boolean isVerify;
    }
}
