package com.gloddy.server.auth.application;

import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.service.UserFactory;
import com.gloddy.server.auth.domain.service.UserSignOutPolicy;
import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.domain.vo.kind.Status;
import com.gloddy.server.auth.exception.WithdrawRequirementsNotMetException;
import com.gloddy.server.auth.jwt.JwtToken;
import com.gloddy.server.auth.jwt.JwtTokenIssuer;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.user.domain.handler.UserCommandHandler;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.user.event.producer.UserEventProducer;
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

    private final UserCommandHandler userCommandHandler;
    private final UserQueryHandler userQueryHandler;
    private final JwtTokenIssuer jwtTokenIssuer;
    private final UserEventProducer userEventProducer;
    private final UserSignOutPolicy userSignOutPolicy;
    private final UserFactory userFactory;

    @Transactional
    public AuthResponse.SignUp signUp(AuthRequest.SignUp req) {
        User created = userFactory.getUser(req);

        User user = userCommandHandler.save(created);

        userEventProducer.produceEvent(new UserCreateEvent(created));
        return AuthResponse.SignUp.from(user, jwtTokenIssuer);
    }

    @Transactional(readOnly = true)
    public AuthResponse.Login login(AuthRequest.Login req) {
        Phone phone = new Phone(req.getPhoneNumber());
        Optional<User> findUser = userQueryHandler.findByPhone(phone);
        return findUser.map(user -> AuthResponse.Login.from(user, jwtTokenIssuer))
                .orElseGet(AuthResponse.Login::fail);
    }

    @Transactional(readOnly = true)
    public AuthResponse.Whether emailCheck(String email) {
        Optional<User> findUser = userQueryHandler.findByEmail(email);

        if (findUser.isEmpty()) {
            return new AuthResponse.Whether(false);
        } else {
            return new AuthResponse.Whether(true);
        }
    }

    @Transactional(readOnly = true)
    public AuthResponse.Token reissueToken(String accessToken, String refreshToken) {
        JwtToken token = jwtTokenIssuer.reIssueToken(accessToken, refreshToken);
        return new AuthResponse.Token(token);
    }

    @Transactional
    public void signOut(Long userId) {
        User user = userQueryHandler.findByIdAndStatus(userId, Status.ACTIVE);
        userSignOutPolicy.validate(user);
        user.withDraw();
    }
}
