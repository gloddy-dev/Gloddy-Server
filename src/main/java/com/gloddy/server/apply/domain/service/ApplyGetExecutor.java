package com.gloddy.server.apply.domain.service;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.dto.ApplyResponse;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.exception.UnAuthorizedApplyGetException;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplyGetExecutor {

    private final GroupQueryHandler groupQueryHandler;
    private final ApplyQueryHandler applyQueryHandler;

    public List<Apply> getAllWaitApply(Long userId, Long groupId) {
        validate(userId, groupId);
        return applyQueryHandler.findAllByGroupIdAndStatus(groupId, Status.WAIT);
    }

    public void validate(Long userId, Long groupId) {
        Group group = groupQueryHandler.findById(groupId);
        if (!group.getCaptain().getId().equals(userId)) {
            throw new UnAuthorizedApplyGetException();
        }
    }

    public Status getStatusByGroupAndUser(Long groupId, Long userId) {
        Apply apply = applyQueryHandler.findOptionalByUserIdAndGroupId(userId, groupId)
                .orElseGet(() -> Apply.builder().status(Status.NONE).build());
        return apply.getStatus();
    }
}
