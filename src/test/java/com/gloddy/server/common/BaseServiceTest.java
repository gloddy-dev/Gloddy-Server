package com.gloddy.server.common;

import com.gloddy.server.apply.infra.repository.ApplyJpaRepository;
import com.gloddy.server.auth.application.AuthService;
import com.gloddy.server.auth.domain.dto.AuthRequest;
import com.gloddy.server.auth.domain.vo.kind.Gender;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import com.gloddy.server.praise.infra.repository.PraiseJpaRepository;
import com.gloddy.server.reliability.infra.repository.ReliabilityRepository;
import com.gloddy.server.user.infra.repository.UserJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@SpringBootTest
@Transactional
public abstract class BaseServiceTest {

    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private PraiseJpaRepository praiseJpaRepository;
    @Autowired
    private ReliabilityRepository reliabilityRepository;
    @Autowired
    private ApplyJpaRepository applyJpaRepository;
    @Autowired
    private GroupJpaRepository groupJpaRepository;
    @Autowired
    private GroupMemberJpaRepository groupMemberJpaRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    protected EntityManager em;

    public Long createUser() {

        Random rand = new Random();
        int number2 = rand.nextInt((9999 - 1000) + 1) + 1000;
        int number3 = rand.nextInt((9999 - 1000) + 1) + 1000;

        AuthRequest.SignUp command = new AuthRequest.SignUp(
                "010" + "-" + number2 + "-" + number3,
                "imageUrl",
                new AuthRequest.SignUp.School(
                        false,
                        "숭실대학교",
                        null
                ),
                "nickName",
                LocalDate.now(),
                Gender.MAIL,
                List.of(Personality.KIND, Personality.ACTIVE, Personality.RESPONSIBLE)
        );

        return authService.signUp(command).getUserId();
    }

    protected void clear() {
        groupMemberJpaRepository.deleteAll();
        applyJpaRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        reliabilityRepository.deleteAll();
        userJpaRepository.deleteAll();
    }
}
