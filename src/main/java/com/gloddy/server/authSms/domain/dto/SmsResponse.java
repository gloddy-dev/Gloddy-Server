package com.gloddy.server.authSms.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SmsResponse {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "AuthSmsVerifyCodeResponse")
    public static class VerifyCode {
        private boolean isVerify;
    }
}
