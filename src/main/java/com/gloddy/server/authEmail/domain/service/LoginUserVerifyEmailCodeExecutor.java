package com.gloddy.server.authEmail.domain.service;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.authSms.utils.VerificationCodeUtil;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserVerifyEmailCodeExecutor {

    private final UserQueryHandler userQueryHandler;
    private final VerificationCodeUtil verificationCodeUtil;

    public void execute(Long userId, String email, String authCode) {
        User user = userQueryHandler.findById(userId);
        verificationCodeUtil.verify(email, authCode);
        user.verifyStudent(email);
    }
}
