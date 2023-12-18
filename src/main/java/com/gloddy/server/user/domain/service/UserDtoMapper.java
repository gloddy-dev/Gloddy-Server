package com.gloddy.server.user.domain.service;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.kind.Personality;
import com.gloddy.server.core.utils.DateTimePatternConstants;
import com.gloddy.server.core.utils.DateTimeUtils;
import com.gloddy.server.core.utils.TimeUtil;
import com.gloddy.server.user.domain.Praise;
import com.gloddy.server.user.domain.Reliability;
import com.gloddy.server.user.domain.dto.UserResponse;

public class UserDtoMapper {

    public static UserResponse.FacadeGet toUserGet(User user, Praise praise, Reliability reliability,
                                            Long participatedGroupCount, Long reviewCount) {
        return new UserResponse.FacadeGet(
                user.getId(),
                user.isCertifiedStudent(),
                user.getImageUrl(),
                user.getNickName(),
                user.getGender().name(),
                TimeUtil.calculateAge(user.getBirth()),
                toStringBirth(user),
                user.getSchool(),
                user.getIntroduce(),
                Personality.of(user.getPersonalities()),
                user.getCountry().getName(),
                user.getCountry().getImage(),
                user.getJoinAt(),
                reliability.getLevel(),
                reliability.getScore(),
                participatedGroupCount,
                reviewCount,
                praise.sumPraiseCount()
        );
    }

    private static String toStringBirth(User user) {
        return DateTimeUtils.dateToString(
                user.getBirth(),
                DateTimePatternConstants.BIRTH
        );
    }
}
