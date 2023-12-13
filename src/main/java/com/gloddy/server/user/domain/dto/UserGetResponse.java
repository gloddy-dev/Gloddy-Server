package com.gloddy.server.user.domain.dto;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Profile;
import com.gloddy.server.user.domain.vo.kind.Personality;
import com.gloddy.server.core.utils.DateTimePatternConstants;
import com.gloddy.server.core.utils.DateTimeUtils;
import com.gloddy.server.core.utils.TimeUtil;
import com.gloddy.server.user.domain.Reliability;
import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class UserGetResponse {
    private Boolean isCertifiedStudent;
    private String imageUrl;
    private String name;
    private String gender;
    private int age;
    private String birth;
    private String school;
    private ReliabilityLevel reliability;
    private int praiseCount;
    private int reviewCount;
    private String introduce;
    private List<String> personalities;
    private LocalDate joinAt;

    public static UserGetResponse of(User user, Profile profile, Reliability reliability,
                                     int praiseCount, int reviewCount
    ) {
        return new UserGetResponse(
            user.isCertifiedStudent(),
            profile.getImageUrl(),
            profile.getNickname(),
            profile.getGender().toString(),
            TimeUtil.calculateAge(profile.getBirth()),
            toStringBirth(user),
            user.getSchool(),
            reliability.getLevel(),
            praiseCount,
            reviewCount,
            profile.getIntroduce(),
            Personality.of(profile.getPersonalities()),
            user.getJoinAt()
        );
    }

    private static String toStringBirth(User user) {
        return DateTimeUtils.dateToString(
                user.getBirth(),
                DateTimePatternConstants.BIRTH
        );
    }
}
