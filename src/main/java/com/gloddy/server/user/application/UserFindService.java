package com.gloddy.server.user.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserFindService {

    private final UserJpaRepository userJpaRepository;

    public User findById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new UserBusinessException(USER_NOT_FOUND));
    }
}
