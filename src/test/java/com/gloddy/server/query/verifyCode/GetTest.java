package com.gloddy.server.query.verifyCode;

import com.gloddy.server.auth.domain.VerifyCode;
import com.gloddy.server.auth.domain.handler.VerifyCodeRepository;
import com.gloddy.server.query.QueryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

public class GetTest extends QueryTest {

    @Autowired
    private VerifyCodeRepository verifyCodeRepository;

    @Test
    @DisplayName("key에 대한 value가 없으면 null을 반환한다.")
    void getNull_when_no_value() {
        String key = verifyCodeRepository.getData("key");

        assertThat(key).isNull();
    }

    @Test
    @DisplayName("key에 대한 데이터가 있으면 value를 반환한다.")
    void getValue_when_exist_value() {
        VerifyCode verifyCode = new VerifyCode("key", "value", 60 * 3L);

        verifyCodeRepository.setData(verifyCode);

        String value = verifyCodeRepository.getData("key");
        assertThat(value).isEqualTo("value");
    }

    @Test
    @DisplayName("만료된 key는 null을 반환한다.")
    void getNull_when_expire() {
        VerifyCode verifyCode = new VerifyCode("key", "value", 0L);

        verifyCodeRepository.setData(verifyCode);
        String value = verifyCodeRepository.getData("key");
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("key에 대한 value 존재를 검사한다. - true")
    void return_true_when_exist() {
        VerifyCode verifyCode = new VerifyCode("key", "value", 60 * 3L);

        verifyCodeRepository.setData(verifyCode);

        Boolean isHas = verifyCodeRepository.hasKey("key");
        assertThat(isHas).isTrue();
    }

    @Test
    @DisplayName("만료된 key에 대한 value 존재를 검사한다.")
    void return_false_when_expire() {
        VerifyCode verifyCode = new VerifyCode("key", "value", 0L);

        verifyCodeRepository.setData(verifyCode);

        Boolean isHas = verifyCodeRepository.hasKey("key");
        assertThat(isHas).isFalse();
    }
}
