package com.gloddy.server.group.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.Group;
import org.springframework.stereotype.Component;

@Component
public class GroupChecker {

    public boolean isMyGroup(User user, Group group) {
        return group.getGroupMemberVOs().existByUserId(user.getId());
    }

    public boolean isGroupCaptain(User user, Group group) {
        return group.getCaptain().equals(user);
    }
}
