package com.gloddy.server.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public class UserResponse {
    @AllArgsConstructor
    @Getter
    public static class MyPage {
        private String imageUrl;
        private String name;
        private String gender;
        private int age;
        private String school;
//        private int score;  // 신뢰도
        private int praiseCount;
        private int reviewCount;
        private String introduce;
        private List<String> personalities;
    }
}
