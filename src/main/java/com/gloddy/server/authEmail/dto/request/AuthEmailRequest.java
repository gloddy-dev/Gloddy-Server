package com.gloddy.server.authEmail.dto.request;

import lombok.Getter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class AuthEmailRequest {
    @Email
    @NotBlank(message = "이메일 정보는 필수입니다.")
    private String email;
}
