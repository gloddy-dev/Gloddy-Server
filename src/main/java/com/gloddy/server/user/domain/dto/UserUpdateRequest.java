package com.gloddy.server.user.domain.dto;

import com.gloddy.server.auth.domain.vo.kind.Gender;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

        @NotBlank
        private String name;

        private LocalDate birth;

        private Gender gender;

        @NotBlank
        private String introduce;

        @NotNull
        @Min(3)
        private List<Personality> personalities;
    }
}
