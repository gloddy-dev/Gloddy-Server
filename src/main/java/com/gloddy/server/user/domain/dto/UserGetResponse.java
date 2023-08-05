package com.gloddy.server.user.domain.dto;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Profile;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.core.utils.TimeUtil;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.vo.ReliabilityLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserGetResponse {
    private String imageUrl;
    private String name;
    private String gender;
    private int age;
    private String school;
    private ReliabilityLevel reliability;
    private int praiseCount;
    private int reviewCount;
    private String introduce;
    private List<String> personalities;

    public static UserGetResponse of(User user, Profile profile, Reliability reliability,
                                     int praiseCount, int reviewCount
    ) {
        return new UserGetResponse(
            profile.getImageUrl(),
            profile.getNickname(),
            profile.getGender().toString(),
            TimeUtil.calculateAge(profile.getBirth()),
            user.getSchool(),
            reliability.getLevel(),
            praiseCount,
            reviewCount,
            profile.getIntroduce(),
            Personality.of(profile.getPersonalities())
        );
    }
}
