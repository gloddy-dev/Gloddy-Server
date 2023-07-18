package com.gloddy.server.authSms.utils;

import com.gloddy.server.authEmail.exception.InvalidVerificationCodeException;
import com.gloddy.server.authSms.domain.dto.SmsResponse;
import com.gloddy.server.core.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeUtil {

    private final RedisUtil redisUtil;
    private final VerificationCodeGenerator verificationCodeGenerator;

    public String generate(String key, long duration) {
        String code = verificationCodeGenerator.generate();
        redisUtil.setDataExpire(key, code, duration);
        return code;
    }

    public Boolean verify(String key, String inputCode) {
        String code = redisUtil.getData(key);
        if(!code.equals(inputCode)) {
            throw new InvalidVerificationCodeException();
        }
        return true;
    }
}
