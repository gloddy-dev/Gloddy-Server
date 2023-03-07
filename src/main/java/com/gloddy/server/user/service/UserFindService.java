package com.gloddy.server.user.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.user.repository.UserRepository;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserFindService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserBusinessException(USER_NOT_FOUND));
    }
}
