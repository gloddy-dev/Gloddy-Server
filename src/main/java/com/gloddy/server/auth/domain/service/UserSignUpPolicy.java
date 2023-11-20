package com.gloddy.server.auth.domain.service;

import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.auth.exception.AlreadyUserSignUpException;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSignUpPolicy {

    private final UserQueryHandler userQueryHandler;

    public void validate(String phoneNumber) {
        Phone phone = new Phone(phoneNumber);

        userQueryHandler.findByPhone(phone)
                .ifPresent(user -> {throw new AlreadyUserSignUpException();});
    }
}
