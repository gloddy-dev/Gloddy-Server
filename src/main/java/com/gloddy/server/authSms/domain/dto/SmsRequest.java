package com.gloddy.server.authSms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class SmsRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Send {
        private String receivingNumber;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyCode {
        private String number;
        private String code;
    }
}
