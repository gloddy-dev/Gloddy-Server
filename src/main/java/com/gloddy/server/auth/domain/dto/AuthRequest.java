package com.gloddy.server.auth.domain.dto;

import com.gloddy.server.user.domain.vo.kind.Gender;
import com.gloddy.server.user.domain.vo.kind.Personality;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "SignUpRequest")
    public static class SignUp{
        private String phoneNumber;
        private String imageUrl;
        private School schoolInfo;
        private String nickname;
        private LocalDate birth;
        private Gender gender;
        private List<Personality> personalities;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class School {
            private boolean isCertifiedStudent;
            private String school;
            private String email;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(name = "LoginRequest")
    public static class Login{
        private String phoneNumber;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailCheck {
        private String email;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReIssueToken {
        private String accessToken;
        private String refreshToken;
    }
}
