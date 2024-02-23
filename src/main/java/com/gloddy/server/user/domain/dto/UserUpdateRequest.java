package com.gloddy.server.user.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gloddy.server.user.domain.vo.kind.Gender;
import com.gloddy.server.user.domain.vo.kind.Personality;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class UserUpdateRequest {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Schema(name = "UserUpdateRequest")
    public static class Info {
        private String imageUrl;

        @NotBlank
        private String name;

        private LocalDate birth;

        private Gender gender;

        @NotBlank
        private String introduce;

        @Size(min = 3)
        private List<Personality> personalities;

        @NotBlank(message = "국적 이름 입력은 필수 입니다.")
        private String countryName;

        @NotBlank(message = "국적 이미지 입력은 필수 입니다.")
        private String countryImage;
    }
}
