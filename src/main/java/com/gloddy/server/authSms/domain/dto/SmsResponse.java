package com.gloddy.server.authSms.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SmsResponse {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyCode {
        private boolean isVerify;
    }
}
