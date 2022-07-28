package com.gloddy.server.auth.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.entity.kind.Personality;
import com.gloddy.server.auth.jwt.JwtTokenBuilder;
import com.gloddy.server.auth.repository.UserRepository;
import com.gloddy.server.core.dto.auth.AuthRequest;
import com.gloddy.server.core.dto.auth.AuthResponse;
import com.gloddy.server.core.error.handler.errorcode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenBuilder jwtTokenBuilder;

    @Transactional
    public AuthResponse.SignUp signUp(AuthRequest.SignUp req) {

        List<Personality> personalities = convertStringToPersonalityEnum(req);

        User created = User.builder()
                .email(req.getEmail())
                .name(req.getName())
                .birth(req.getBirth())
                .gender(req.getGender())
                .personalities(personalities)
                .build();

        User user = userRepository.save(created);

        return new AuthResponse.SignUp(user.getId(), user.getAuthority().getRole());

    }

    private List<Personality> convertStringToPersonalityEnum(AuthRequest.SignUp req) {
        return req.getPersonalities().stream()
                .map(personality -> Personality.valueOf(personality))
                .collect(Collectors.toUnmodifiableList());
    }

    public AuthResponse.Login login(String email) {

        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserBusinessException(ErrorCode.USER_NOT_FOUND));

        String token = jwtTokenBuilder.createToken(email);

        return new AuthResponse.Login(findUser.getId(), findUser.getAuthority().getRole(), token);
    }




}
