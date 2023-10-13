package com.gloddy.server.query.verifyCode;


import com.gloddy.server.auth.domain.VerifyCode;
import com.gloddy.server.auth.domain.handler.VerifyCodeRepository;
import com.gloddy.server.query.QueryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

public class SetTest extends QueryTest {

    @Autowired
    private VerifyCodeRepository verifyCodeRepository;

    @Test
    @DisplayName("value가 없는 key에 value를 넣는다.")
    void setValue_when_no_value() {
        //given
        VerifyCode verifyCode = new VerifyCode(
                "key",
                "value",
                60 * 3L
        );
        //when
        verifyCodeRepository.setData(verifyCode);

        //then
        String value = verifyCodeRepository.getData("key");
        assertThat(value).isEqualTo("value");
    }

    @Test
    @DisplayName("이미 value가 있는 key에 새로운 value를 넣는다.")
    void setValue_when_exist_value() {
        //given
        VerifyCode existVerifyCode = new VerifyCode(
                "key",
                "existValue",
                60 * 3L
        );
        verifyCodeRepository.setData(existVerifyCode);

        //when
        VerifyCode newVerifyCode = new VerifyCode(
                "key",
                "newValue",
                60 * 3L
        );

        verifyCodeRepository.setData(newVerifyCode);

        //then
        String value = verifyCodeRepository.getData("key");
        assertThat(value).isEqualTo("newValue");
    }
}
