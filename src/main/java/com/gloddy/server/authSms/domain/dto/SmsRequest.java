package com.gloddy.server.authSms.domain.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class SmsRequest {

    public final static int PHONE_NUMBER_LENGTH = 11;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Send {
        @Size(min = PHONE_NUMBER_LENGTH, max = PHONE_NUMBER_LENGTH, message = "휴대폰 번호 길이는 11입니다.")
        @Pattern(regexp = "^[0-9]*$", message = "휴대폰 번호는 숫자만 입력 가능합니다.")
        private String number;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VerifyCode {
        @Size(min = PHONE_NUMBER_LENGTH, max = PHONE_NUMBER_LENGTH, message = "휴대폰 번호 길이는 11입니다.")
        @Pattern(regexp = "^[0-9]*$", message = "휴대폰 번호는 숫자만 입력 가능합니다.")
        private String number;
        private String code;
    }
}
