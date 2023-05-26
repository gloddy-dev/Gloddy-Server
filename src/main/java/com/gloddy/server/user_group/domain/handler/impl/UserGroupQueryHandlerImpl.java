package com.gloddy.server.user_group.domain.handler.impl;

import com.gloddy.server.user_group.domain.UserGroup;
import com.gloddy.server.user_group.domain.handler.UserGroupQueryHandler;
import com.gloddy.server.user_group.infra.repository.UserGroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserGroupQueryHandlerImpl implements UserGroupQueryHandler {

    private final UserGroupJpaRepository userGroupJpaRepository;

    @Override
    public UserGroup findByUserIdAndGroupId(Long userId, Long groupId) {
        return userGroupJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("Not Found UserGroup"));
    }
}
