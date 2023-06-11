package com.gloddy.server.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.auth.jwt.JwtTokenBuilder;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.praise.infra.repository.PraiseJpaRepository;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.infra.repository.ReliabilityRepository;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
abstract public class BaseApiTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected JwtTokenBuilder jwtTokenBuilder;

    @Autowired
    protected UserJpaRepository userJpaRepository;

    @Autowired
    protected PraiseJpaRepository praiseJpaRepository;

    @Autowired
    protected ReliabilityQueryHandler reliabilityQueryHandler;

    @Autowired
    protected ReliabilityRepository reliabilityRepository;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected EntityManager em;

    protected String testUserEmail = "testEmail@soogsil.ac.kr";
    protected String accessToken;
    protected User user;
    protected Reliability loginUserReliability;

    protected Praise createPraise(User user) {
        Praise mockPraise = Praise.empty();
        mockPraise.init(user);
        return praiseJpaRepository.save(mockPraise);
    }

    protected User createLoginUser() {
        User mockUser = User.builder().email(testUserEmail).
                personalities(List.of(Personality.KIND)).build();
        return userJpaRepository.save(mockUser);
    }

    private Reliability createReliability(User user) {
        Reliability reliability = new Reliability(user);
        return reliabilityQueryHandler.save(reliability);
    }

    protected User createUser() {
        User user = User.builder().email(UUID.randomUUID().toString() + "@soongsil.ac.kr")
                .personalities(List.of(Personality.KIND)).build();
        return userJpaRepository.save(user);
    }

    protected String getTokenAfterLogin(User user) {
        return jwtTokenBuilder.createToken(user.getEmail());
    }

    @BeforeEach
    void setUp() {
        User mockUser = createLoginUser();
        createPraise(mockUser);
        loginUserReliability = createReliability(mockUser);
        accessToken = getTokenAfterLogin(mockUser);
        user = mockUser;
    }
}
