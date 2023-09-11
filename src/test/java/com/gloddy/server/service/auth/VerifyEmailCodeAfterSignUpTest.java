package com.gloddy.server.service.auth;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.authEmail.application.AuthEmailService;
import com.gloddy.server.authEmail.domain.dto.request.AuthEmailRequest;
import com.gloddy.server.common.users.UserServiceTest;
import com.gloddy.server.core.utils.RedisUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.AfterTransaction;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class VerifyEmailCodeAfterSignUpTest extends UserServiceTest {

    @Autowired
    private AuthEmailService authEmailService;

    @MockBean
    private RedisUtil redisUtil;

    @Nested
    class Case1 {

        private Long userId;

        @Test
        @Commit
        @DisplayName("학교 이메일 인증 안한 로그인 유저가 이메일 인증을 성공합니다.")
        void verifyEmailCodeAfterSignUp_success() {

            //given
            //학교 이메일 인증을 안한 유저를 생성합니다.
            userId = createUser();
        }

        @AfterTransaction
        @Commit
        void when_and_then() {
            String email = "test@soongsil.ac.kr";
            String authCode = "999999";

            willReturn(authCode)
                    .given(redisUtil).getData(email);

            AuthEmailRequest.AuthCode request = new AuthEmailRequest.AuthCode(email, authCode);

            //when
            authEmailService.verifyEmailCodeAfterSignUp(userId, request);

            //then
            User user = userJpaRepository.findById(userId).orElseThrow();
            assertThat(user.isCertifiedStudent()).isTrue();
            assertThat(user.getEmail()).isEqualTo(email);

            //after
            clear();
        }

    }
}
