package com.gloddy.server.user.domain.handler.impl;

import com.gloddy.server.user.application.internal.dto.UserPreviewResponse;
import com.gloddy.server.user.application.internal.dto.UserPreviewsResponse;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.user.domain.dto.PraiseResponse;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
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
    public User findByIdFetch(Long id) {
        return userJpaRepository.findByIdFetch(id)
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

    @Override
    public UserPreviewResponse findUserPreviewById(Long userId) {
        return userJpaRepository.findUserPreviewById(userId)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public UserPreviewsResponse findUserPreviewsByInId(Collection<Long> userIds) {
        List<UserPreviewResponse> userPreviews = userJpaRepository.findUserPreviewsByInId(userIds);
        return new UserPreviewsResponse(userPreviews);
    }
}
