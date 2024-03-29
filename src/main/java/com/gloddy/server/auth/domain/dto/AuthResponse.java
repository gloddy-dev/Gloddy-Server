package com.gloddy.server.auth.domain.dto;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.auth.jwt.JwtToken;
import com.gloddy.server.auth.jwt.JwtTokenIssuer;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "SignUpResponse")
    public static class SignUp extends AuthResponse {
        private Long userId;
        private String authority;
        private JwtToken token;

        public static SignUp from(User user, JwtTokenIssuer jwtTokenIssuer) {
            return new SignUp(user.getId(), user.getAuthority().getRole(),
                    jwtTokenIssuer.issueToken(user.getId().toString()));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Schema(name = "LoginResponse")
    public static class Login extends AuthResponse {
        private boolean isExistUser;
        private Long userId;
        private String authority;
        private JwtToken token;

        public static Login fail() {
            return new Login(false, null, null, null);
        }

        public static Login from(User user, JwtTokenIssuer jwtTokenIssuer) {
            return new Login(true, user.getId(), user.getAuthority().getRole(),
                    jwtTokenIssuer.issueToken(user.getId().toString()));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Whether extends AuthResponse {
        private Boolean aBoolean;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Token extends AuthResponse {
        private JwtToken token;
    }
}
