package com.gloddy.server.auth.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.service.UserFactory;
import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.jwt.JwtTokenBuilder;
import com.gloddy.server.user.domain.handler.UserCommandHandler;
import com.gloddy.server.user.event.producer.UserEventProducer;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import com.gloddy.server.auth.domain.dto.AuthRequest;
import com.gloddy.server.auth.domain.dto.AuthResponse;
import com.gloddy.server.user.event.UserCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final UserCommandHandler userCommandHandler;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final UserEventProducer userEventProducer;
    private final UserFactory userFactory;

    @Transactional
    public AuthResponse.SignUp signUp(AuthRequest.SignUp req) {
        User created = userFactory.getUser(req);

        User user = userCommandHandler.save(created);

        userEventProducer.produceEvent(new UserCreateEvent(created));
        return AuthResponse.SignUp.from(user, jwtTokenBuilder);
    }

    @Transactional(readOnly = true)
    public AuthResponse.Login login(AuthRequest.Login req) {
        Optional<User> findUser = userJpaRepository.findByPhone(new Phone(req.getPhoneNumber()));
        return findUser.map(user -> AuthResponse.Login.from(user, jwtTokenBuilder))
                .orElseGet(AuthResponse.Login::fail);
    }

    @Transactional(readOnly = true)
    public AuthResponse.Whether emailCheck(String email) {

        Optional<User> findUser = userJpaRepository.findByEmail(email);

        if (findUser.isEmpty()) {
            return new AuthResponse.Whether(false);
        } else {
            return new AuthResponse.Whether(true);
        }
    }
}
