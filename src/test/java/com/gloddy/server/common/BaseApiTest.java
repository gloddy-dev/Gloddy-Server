package com.gloddy.server.common;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.entity.kind.Personality;
import com.gloddy.server.auth.jwt.JwtTokenBuilder;
import com.gloddy.server.estimate.entity.Praise;
import com.gloddy.server.estimate.repository.PraiseJpaRepository;
import com.gloddy.server.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
abstract public class BaseApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected JwtTokenBuilder jwtTokenBuilder;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PraiseJpaRepository praiseJpaRepository;

    protected String testUserEmail = "testEmail@soogsil.ac.kr";
    protected String accessToken;
    protected User user;

    protected Praise createPraise(User user) {
        Praise mockPraise = Praise.empty();
        mockPraise.init(user);
        return praiseJpaRepository.save(mockPraise);
    }

    protected User createUser() {
        User mockUser = User.builder().email(testUserEmail).
                personalities(List.of(Personality.KIND)).build();
        return userRepository.save(mockUser);
    }

    protected String getTokenAfterLogin(User user) {
        return jwtTokenBuilder.createToken(user.getEmail());
    }

    @BeforeEach
    void setUp() {
        User mockUser = createUser();
        createPraise(mockUser);
        accessToken = getTokenAfterLogin(mockUser);
        user = mockUser;
    }
}
