package com.gloddy.server.authSms.utils;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.VerificationCodeBusinessException;
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

    public void verify(String key, String inputCode) {
        validateKey(key);
        validateCode(key, inputCode);
    }

    private void validateKey(String key) {
        if(!redisUtil.hasKey(key)) {
            throw new VerificationCodeBusinessException(ErrorCode.VERIFICATION_CODE_EXPIRED);
        }
    }

    private void validateCode(String key, String inputCode) {
        String code = redisUtil.getData(key);
        if(!code.equals(inputCode)) {
            throw new VerificationCodeBusinessException(ErrorCode.VERIFICATION_CODE_INVALID);
        }
    }
}
