package com.gloddy.server.core.dto.auth;

import com.gloddy.server.core.error.handler.errorcode.ErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuthResponse {

    private ErrorCode errorCode = ErrorCode.NULL;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SignUp extends AuthResponse{
        private Long userId;
        private String authority;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Login extends AuthResponse{
        private Long userId;
        private String authority;
        private String token;
    }
}
