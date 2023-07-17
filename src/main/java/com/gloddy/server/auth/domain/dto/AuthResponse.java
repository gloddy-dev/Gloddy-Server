package com.gloddy.server.auth.domain.dto;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.jwt.JwtTokenBuilder;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
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
    public static class SignUp extends AuthResponse {
        private Long userId;
        private String authority;
        private String accessToken;
        private String refreshToken;

        public static SignUp from(User user, JwtTokenBuilder jwtTokenBuilder) {
            return new SignUp(user.getId(), user.getAuthority().getRole(),
                    jwtTokenBuilder.createAccessToken(user.getPhone().getPhoneNumber()),
                    jwtTokenBuilder.createRefreshToken(user.getPhone().getPhoneNumber()));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Login extends AuthResponse {
        private boolean isExistUser;
        private Long userId;
        private String authority;
        private String accessToken;
        private String refreshToken;

        public static Login fail() {
            return new Login(false, null, null, null, null);
        }

        public static Login from(User user, JwtTokenBuilder jwtTokenBuilder) {
            return new Login(true, user.getId(), user.getAuthority().getRole(),
                    jwtTokenBuilder.createAccessToken(user.getPhone().getPhoneNumber()),
                    jwtTokenBuilder.createRefreshToken(user.getPhone().getPhoneNumber()));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Whether extends AuthResponse {
        private Boolean aBoolean;
    }
}
