package com.gloddy.server.auth.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.exception.AlreadyUserSignUpException;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSignUpPolicy {

    private final UserJpaRepository userJpaRepository;

    public void validate(String phoneNumber) {
        Phone phone = new Phone(phoneNumber);

        Optional<User> user = userJpaRepository.findByPhone(phone);

        if (user.isPresent()) {
            throw new AlreadyUserSignUpException();
        }
    }
}
