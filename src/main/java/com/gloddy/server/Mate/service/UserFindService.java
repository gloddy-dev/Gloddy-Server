package com.gloddy.server.Mate.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.repository.UserRepository;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserFindService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserBusinessException(USER_NOT_FOUND));
    }
}
