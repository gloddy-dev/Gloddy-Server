package com.gloddy.server.authEmail.domain.dto.request;

import lombok.Getter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
