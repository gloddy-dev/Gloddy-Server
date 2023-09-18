package com.gloddy.server.authEmail.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.authEmail.exception.InvalidVerificationCodeException;
import com.gloddy.server.core.utils.RedisUtil;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserVerifyEmailCodeExecutor {

    private final UserQueryHandler userQueryHandler;
    private final RedisUtil redisUtil;

    public void execute(Long userId, String email, String authCode) {
        User user = userQueryHandler.findById(userId);
        validateEmailCode(email, authCode);
        user.verifyStudent(email);
    }

    private void validateEmailCode(String email, String authCode) {
        String code = redisUtil.getData(email);
        if (!code.equals(authCode)) {
            throw new InvalidVerificationCodeException();
        }
    }
}
