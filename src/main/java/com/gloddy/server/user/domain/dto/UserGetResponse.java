package com.gloddy.server.user.domain.dto;

import com.gloddy.server.reliability.domain.vo.ReliabilityLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class UserResponse {
    @AllArgsConstructor
    @Getter
    public static class Get {
        private String imageUrl;
        private String name;
        private String gender;
        private int age;
        private String school;
        private ReliabilityLevel level;
        private int praiseCount;
        private int reviewCount;
        private String introduce;
        private List<String> personalities;
    }

    @AllArgsConstructor
    @Getter
    public static class Update {
        private String imageUrl;
        private String name;
        private LocalDate birth;
        private String gender;
    }

    @AllArgsConstructor
    @Getter
    public static class
}
