package com.gloddy.server.user.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.PraiseBusinessException;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.mate.infra.repository.MateJpaRepository;
import com.gloddy.server.praise.infra.repository.PraiseJpaRepository;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.handler.ReliabilityQueryHandler;
import com.gloddy.server.user.domain.dto.UserGetResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGetService {
    private final UserQueryHandler userQueryHandler;
    private final PraiseJpaRepository praiseJpaRepository;
    private final MateJpaRepository mateJpaRepository;
    private final ReliabilityQueryHandler reliabilityQueryHandler;

    @Transactional(readOnly = true)
    public UserGetResponse getMyPage(Long userId) {
        User user = userQueryHandler.findById(userId);
        Praise praise = praiseJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new PraiseBusinessException(ErrorCode.PRAISE_NOT_FOUND));
        Reliability reliability = reliabilityQueryHandler.findByUserId(userId);
        int reviewCount = mateJpaRepository.countByMateId(userId).intValue();

        return UserGetResponse.of(
                user,
                user.getProfile(),
                reliability,
                sumPraiseCount(praise),
                reviewCount
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
