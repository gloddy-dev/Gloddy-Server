package com.gloddy.server.user.domain.dto;

import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class UserResponse {

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class ExistNickname {
        private Boolean isExistNickname;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class FacadeGet {
        private Long userId;
        private Boolean isCertifiedStudent;
        private String imageUrl;
        private String nickname;
        private String gender;
        private int age;
        private String birth;
        private String school;
        private String introduce;
        private List<String> personalities;
        private LocalDate joinAt;
        private ReliabilityLevel reliabilityLevel;
        private Long reliabilityScore;
        private Long participatedGroupCount;
        private Long reviewCount;
        private int praiseCount;
    }
}
