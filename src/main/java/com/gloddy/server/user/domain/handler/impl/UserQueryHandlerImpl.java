package com.gloddy.server.user.domain.handler.impl;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQueryHandlerImpl implements UserQueryHandler {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User findById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByProfile_Nickname(nickname);
    }
}
