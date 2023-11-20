package com.gloddy.server.auth.domain.service;

import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.auth.exception.WithdrawRequirementsNotMetException;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSignOutPolicy {

    private final GroupMemberQueryHandler groupMemberQueryHandler;
    private final ApplyQueryHandler applyQueryHandler;

    public void validate(User user) {
        validateGroup(user);
        validateApply(user);
    }

    private void validateGroup(User user) {
        if (existsParticipationGroup(user)) {
            throw new WithdrawRequirementsNotMetException(ErrorCode.EXISTS_PARTICIPATING_GROUP);
        }
    }

    private void validateApply(User user) {
        if (existsWaitApply(user)) {
            throw new WithdrawRequirementsNotMetException(ErrorCode.EXISTS_WAIT_APPLY);
        }
    }

    private boolean existsParticipationGroup(User user) {
        return groupMemberQueryHandler.existsByUserAndGroupEndTimeBefore(user);
    }

    private boolean existsWaitApply(User user) {
        return applyQueryHandler.existsByUserAndStatus(user, com.gloddy.server.apply.domain.vo.Status.WAIT);
    }
}
