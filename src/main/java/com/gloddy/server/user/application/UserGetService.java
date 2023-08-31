package com.gloddy.server.user.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.PraiseBusinessException;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.mate.infra.repository.MateJpaRepository;
import com.gloddy.server.praise.domain.handler.PraiseQueryHandler;
import com.gloddy.server.praise.infra.repository.PraiseJpaRepository;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.handler.ReliabilityQueryHandler;
import com.gloddy.server.user.domain.dto.UserGetResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.user.domain.service.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGetService {
    private final UserQueryHandler userQueryHandler;
    private final PraiseQueryHandler praiseQueryHandler;
    private final MateJpaRepository mateJpaRepository;
    private final ReliabilityQueryHandler reliabilityQueryHandler;

    @Transactional(readOnly = true)
    public UserGetResponse getUserPage(Long userId) {
        User user = userQueryHandler.findById(userId);
        Praise praise = praiseQueryHandler.findByUserId(userId);
        Reliability reliability = reliabilityQueryHandler.findByUserId(userId);

        return UserDtoMapper.toUserGet(user, reliability, praise, getReviewCount(userId));
    }

    private int getReviewCount(Long userId) {
        return mateJpaRepository.countByMateId(userId).intValue();
    }
}
