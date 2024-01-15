package com.gloddy.server.apply.domain.service;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.exception.AlreadyProcessedApplyException;
import com.gloddy.server.apply.exception.CantAcceptMoreGroupMemberException;
import com.gloddy.server.apply.exception.UnAuthorizedApplyStatusUpdateException;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplyStatusUpdatePolicy {

    public void validate(Long commandUserId, Status commandStatus, Apply apply) {
        validateIsGroupCaptain(commandUserId, apply.getGroup());
        validateAlreadyProcessedApply(apply);
        if (commandStatus.isApprove()) {
            validateCanAcceptMoreGroupMembers(apply.getGroup());
        }
    }

    private void validateIsGroupCaptain(Long userId, Group group) {
        if (!isCaptain(userId, group)) {
            throw new UnAuthorizedApplyStatusUpdateException();
        }
    }

    private void validateCanAcceptMoreGroupMembers(Group group) {
        if (!group.canAcceptMoreMembers()) {
            throw new CantAcceptMoreGroupMemberException();
        }
    }

    private void validateAlreadyProcessedApply(Apply apply) {
        if (!apply.getStatus().isWait()) {
            throw new AlreadyProcessedApplyException();
        }
    }

    private boolean isCaptain(Long userId, Group group) {
        return userId.equals(group.getCaptain().getId());
    }
}
