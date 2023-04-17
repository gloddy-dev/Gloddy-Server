package com.gloddy.server.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.entity.kind.Personality;
import com.gloddy.server.auth.jwt.JwtTokenBuilder;
import com.gloddy.server.estimate.entity.Praise;
import com.gloddy.server.estimate.repository.PraiseJpaRepository;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.repository.ReliabilityRepository;
import com.gloddy.server.user.repository.UserRepository;
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
    protected UserRepository userRepository;

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

    protected Praise createPraise(User user) {
        Praise mockPraise = Praise.empty();
        mockPraise.init(user);
        return praiseJpaRepository.save(mockPraise);
    }

    protected User createLoginUser() {
        User mockUser = User.builder().email(testUserEmail).
                personalities(List.of(Personality.KIND)).build();
        return userRepository.save(mockUser);
    }

    public Reliability createReliability(User user) {
        Reliability reliability = new Reliability(user);
        return reliabilityQueryHandler.save(reliability);
    }

    protected User createUser() {
        User user = User.builder().email(UUID.randomUUID().toString() + "@soongsil.ac.kr")
                .personalities(List.of(Personality.KIND)).build();
        return userRepository.save(user);
    }

    protected String getTokenAfterLogin(User user) {
        return jwtTokenBuilder.createToken(user.getEmail());
    }

    @BeforeEach
    void setUp() {
        User mockUser = createLoginUser();
        createPraise(mockUser);
        createReliability(mockUser);
        accessToken = getTokenAfterLogin(mockUser);
        user = mockUser;
    }
}
