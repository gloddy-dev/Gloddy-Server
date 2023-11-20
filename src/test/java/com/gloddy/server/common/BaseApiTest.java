package com.gloddy.server.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.user.domain.vo.Phone;
import com.gloddy.server.user.domain.vo.Profile;
import com.gloddy.server.user.domain.vo.School;
import com.gloddy.server.user.domain.vo.kind.Personality;
import com.gloddy.server.auth.jwt.JwtTokenBuilder;
import com.gloddy.server.auth.jwt.payload.AccessPayload;
import com.gloddy.server.user.domain.Reliability;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

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
    protected ObjectMapper objectMapper;

    @Autowired
    protected EntityManager em;

    protected String testUserEmail = "testEmail@soogsil.ac.kr";
    protected String school = "숭실대학교";
    protected String testPhoneNumber = "010-0000-0000";
    protected String accessToken;
    protected User user;
    protected Reliability loginUserReliability;

    private String getPhoneNumber() {
        Random rand = new Random();
        int number2 = rand.nextInt((9999 - 1000) + 1) + 1000;
        int number3 = rand.nextInt((9999 - 1000) + 1) + 1000;
        return "010" + "-" + number2 + "-" + number3;
    }

    protected User createLoginUser() {
        Profile profile = Profile.builder()
                .personalities(List.of(Personality.KIND))
                .build();
        User mockUser = User.builder().phone(new Phone(testPhoneNumber))
                .school(School.createNoCertified(school))
                .profile(profile)
                .build();
        return userJpaRepository.save(mockUser);
    }

    protected User createUser() {
        Profile profile = Profile.builder()
                .personalities(List.of(Personality.KIND))
                .build();
        User mockUser = User.builder().phone(new Phone(getPhoneNumber()))
                .school(School.createNoCertified(school))
                .profile(profile)
                .build();
        return userJpaRepository.save(mockUser);
    }

    protected String getTokenAfterLogin(User user) {
        AccessPayload accessPayload = AccessPayload.of(user.getId().toString());
        return jwtTokenBuilder.createToken(accessPayload);
    }

    @BeforeEach
    void setUp() {
        User mockUser = createLoginUser();
        accessToken = getTokenAfterLogin(mockUser);
        user = mockUser;
    }
}
