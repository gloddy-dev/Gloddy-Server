package com.gloddy.server.authSms.infra;

import com.gloddy.server.authEmail.exception.InvalidVerificationCodeException;
import com.gloddy.server.authSms.infra.dto.SmsNumberRequest;
import com.gloddy.server.authSms.infra.utils.VerificationCodeGenerator;
import com.gloddy.server.core.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeService {

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
