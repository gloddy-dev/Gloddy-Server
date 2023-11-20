package com.gloddy.server.user.domain.handler.impl;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.user.domain.vo.kind.Status;
import com.gloddy.server.user.domain.dto.PraiseResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
    public User findByIdAndStatus(Long id, Status status) {
        return userJpaRepository.findByIdAndStatusFetch(id, status)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return userJpaRepository.existsByProfile_Nickname(nickname);
    }

    @Override
    public Optional<User> findByPhone(Phone phone) {
        return userJpaRepository.findByPhone(phone);
    }

    @Override
    public PraiseResponse.GetPraiseForUser findPraiseDtoByUserId(Long userId) {
        return userJpaRepository.findPraiseByUserId(userId);
    }
}
