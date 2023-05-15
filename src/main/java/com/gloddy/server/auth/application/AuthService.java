package com.gloddy.server.auth.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.auth.jwt.JwtTokenBuilder;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import com.gloddy.server.auth.domain.dto.AuthRequest;
import com.gloddy.server.auth.domain.dto.AuthResponse;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import com.gloddy.server.core.event.user.UserCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final JwtTokenBuilder jwtTokenBuilder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public AuthResponse.SignUp signUp(AuthRequest.SignUp req) {

        List<Personality> personalities = convertStringToPersonalityEnum(req);

        User created = User.builder()
                .email(req.getEmail())
                .password(req.getPassword())
                .name(req.getName())
                .school(req.getSchool())
                .birth(req.getBirth())
                .gender(req.getGender())
                .personalities(personalities)
                .build();

        applicationEventPublisher.publishEvent(new UserCreateEvent(created));

        User user = userJpaRepository.save(created);

        return new AuthResponse.SignUp(user.getId(), user.getAuthority().getRole());

    }

    private List<Personality> convertStringToPersonalityEnum(AuthRequest.SignUp req) {
        return req.getPersonalities().stream()
                .map(personality -> Personality.valueOf(personality))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional(readOnly = true)
    public AuthResponse.Login login(AuthRequest.Login req) {

        User findUser = userJpaRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));

        if (!req.getPassword().equals(findUser.getPassword())) {
            throw new UserBusinessException(ErrorCode.PASSWORD_DISCORD);
        }

        String token = jwtTokenBuilder.createToken(req.getEmail());

        return new AuthResponse.Login(findUser.getId(), findUser.getAuthority().getRole(), token);
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
