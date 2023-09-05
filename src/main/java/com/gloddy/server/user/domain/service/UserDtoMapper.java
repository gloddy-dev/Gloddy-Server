package com.gloddy.server.user.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.core.utils.DateTimePatternConstants;
import com.gloddy.server.core.utils.DateTimeUtils;
import com.gloddy.server.core.utils.TimeUtil;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.user.api.dto.UserResponse;
import com.gloddy.server.user.domain.dto.UserGetResponse;

public class UserDtoMapper {

    public static UserGetResponse toUserGet(User user, Reliability reliability, Praise praise, int reviewCount) {
        return new UserGetResponse(
                user.isCertifiedStudent(),
                user.getImageUrl(),
                user.getNickName(),
                user.getGender().name(),
                TimeUtil.calculateAge(user.getBirth()),
                toStringBirth(user),
                user.getSchool(),
                reliability.getLevel(),
                praise.sumPraiseCount(),
                reviewCount,
                user.getIntroduce(),
                Personality.of(user.getPersonalities()),
                user.getJoinAt()
        );
    }

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
