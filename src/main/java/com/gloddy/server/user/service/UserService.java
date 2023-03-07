package com.gloddy.server.user.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.entity.kind.Personality;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.PraiseBusinessException;
import com.gloddy.server.core.utils.TimeUtil;
import com.gloddy.server.estimate.entity.Praise;
import com.gloddy.server.estimate.repository.MateJpaRepository;
import com.gloddy.server.estimate.repository.PraiseJpaRepository;
import com.gloddy.server.user.handler.UserQueryHandler;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gloddy.server.user.dto.UserResponse.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserQueryHandler userQueryHandler;
    private final PraiseJpaRepository praiseJpaRepository;
    private final MateJpaRepository mateJpaRepository;

    @ApiOperation("마이페이지 조회")
    @Transactional(readOnly = true)
    public MyPage getMyPage(Long userId) {
        User user = userQueryHandler.findById(userId);
        Praise praise = praiseJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new PraiseBusinessException(ErrorCode.PRAISE_NOT_FOUND));
        int reviewCount = mateJpaRepository.countByMateId(userId).intValue();

        return new MyPage(
                user.getImageUrl(),
                user.getName(),
                user.getGender().toString(),
                TimeUtil.calculateAge(user.getBirth()),
                user.getSchool(),
                sumPraiseCount(praise),
                reviewCount,
                user.getIntroduce(),
                Personality.of(user.getPersonalities())
        );
    }

    private int sumPraiseCount(Praise praise) {
        return praise.getTotalCalmCount()
                + praise.getTotalKindCount()
                + praise.getTotalActiveCount()
                + praise.getTotalHumorCount()
                + praise.getTotalAbsenceCount();
    }
}
