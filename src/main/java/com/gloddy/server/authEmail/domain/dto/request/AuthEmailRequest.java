package com.gloddy.server.authEmail.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AuthEmailRequest {

    @Getter
    public static class AuthEmail {
        @Email
        @NotBlank(message = "이메일 정보는 필수입니다.")
        private String email;
    }

    @Getter
    public static class AuthCode {
        @Email
        private String email;
        private String authCode;
    }
}
