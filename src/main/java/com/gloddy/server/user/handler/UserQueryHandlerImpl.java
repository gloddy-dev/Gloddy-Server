package com.gloddy.server.user.handler;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.user.repository.UserRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserHandlerImpl implements UserHandler {

    private final UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));
    }


}
