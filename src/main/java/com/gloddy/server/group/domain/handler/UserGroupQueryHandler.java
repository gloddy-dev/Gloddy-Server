package com.gloddy.server.group.domain.handler;

import com.gloddy.server.group.domain.UserGroup;

public interface UserGroupQueryHandler {
    UserGroup findByUserIdAndGroupId(Long userId, Long groupId);
}
