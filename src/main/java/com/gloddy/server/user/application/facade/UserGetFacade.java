package com.gloddy.server.user.application.facade;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.kind.Status;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.mate.domain.handler.MateQueryHandler;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.praise.domain.handler.PraiseQueryHandler;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.handler.ReliabilityQueryHandler;
import com.gloddy.server.user.api.dto.UserResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.user.domain.service.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGetFacade {

    private final UserQueryHandler userQueryHandler;
    private final PraiseQueryHandler praiseQueryHandler;
    private final ReliabilityQueryHandler reliabilityQueryHandler;
    private final MateQueryHandler mateQueryHandler;
    private final GroupMemberQueryHandler groupMemberQueryHandler;

    @Transactional(readOnly = true)
    public UserResponse.FacadeGet getUserFacade(Long userId) {
        User user = userQueryHandler.findByIdAndStatus(userId, Status.ACTIVE);
        Praise praise = praiseQueryHandler.findByUserId(userId);
        Reliability reliability = reliabilityQueryHandler.findByUserId(userId);
        Long countParticipatedGroup = groupMemberQueryHandler.countParticipatedGroup(userId);
        Long reviewCount = mateQueryHandler.countByUserId(userId);

        return UserDtoMapper.toUserGet(
                user, praise, reliability, countParticipatedGroup, reviewCount
        );
    }
}
