package com.gloddy.server.core.dto.auth;

import com.gloddy.server.auth.entity.kind.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class SignUp{
        private String email;
        private String name;
        private LocalDate birth;
        private Gender gender;
        private List<String> personalities;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Login{
        private String email;
    }
}
