package com.gloddy.server.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserResponse {
    @AllArgsConstructor
    @Getter
    public static class MyPage {
        private String imageUrl;
        private String name;
        private String gender;
        private String school;
        private int score;
//        private int praiseScore;
        private Long reviewCount;
//        private String introduce;
//        private List<String> personalities;
    }
}
