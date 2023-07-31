package com.gloddy.server.user.domain.dto;

import com.gloddy.server.auth.domain.vo.kind.Gender;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class UserUpdateRequest {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info {
        private String imageUrl;
        private String name;
        private LocalDate birth;
        private Gender gender;
        private String introduce;
        private List<Personality> personalities;
    }
}
