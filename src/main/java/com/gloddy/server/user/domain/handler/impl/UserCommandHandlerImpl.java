package com.gloddy.server.user.domain.handler.impl;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.handler.UserCommandHandler;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserCommandHandlerImpl implements UserCommandHandler {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }
}
