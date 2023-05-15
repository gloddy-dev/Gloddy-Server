package com.gloddy.server.apply.domain.service;

import com.gloddy.server.apply.exception.UnAuthorizedApplyStatusUpdateException;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplyStatusUpdatePolicy {

    public void validate(User user, Group group) {
        if (!isCaptain(user, group)) {
            throw new UnAuthorizedApplyStatusUpdateException();
        }
    }

    private boolean isCaptain(User user, Group group) {
        return user.equals(group.getCaptain());
    }
}
