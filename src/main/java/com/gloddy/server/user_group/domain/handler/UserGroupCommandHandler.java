package com.gloddy.server.user_group.domain.handler;

import com.gloddy.server.user_group.domain.UserGroup;

public interface UserGroupCommandHandler {
    UserGroup save(UserGroup userGroup);
}
