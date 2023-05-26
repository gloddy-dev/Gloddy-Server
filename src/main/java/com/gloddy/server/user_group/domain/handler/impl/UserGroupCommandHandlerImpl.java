package com.gloddy.server.user_group.domain.handler.impl;

import com.gloddy.server.user_group.domain.UserGroup;
import com.gloddy.server.user_group.domain.handler.UserGroupCommandHandler;
import com.gloddy.server.user_group.infra.repository.UserGroupJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserGroupCommandHandlerImpl implements UserGroupCommandHandler {
    private final UserGroupJpaRepository userGroupJpaRepository;


    @Override
    public UserGroup save(UserGroup userGroup) {
        return userGroupJpaRepository.save(userGroup);
    }
}
