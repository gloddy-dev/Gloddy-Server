package com.gloddy.server.authSms.utils;

import com.gloddy.server.auth.domain.VerifyCode;
import com.gloddy.server.auth.domain.handler.VerifyCodeRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.VerificationCodeBusinessException;
import com.gloddy.server.core.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationCodeUtil {

    private final VerifyCodeRepository verifyCodeRepository;
    private final VerificationCodeGenerator verificationCodeGenerator;

    public String generate(String key, long duration) {
        String code = verificationCodeGenerator.generate();
        VerifyCode verifyCode = new VerifyCode(key, code, duration);
        verifyCodeRepository.setData(verifyCode);
        return code;
    }

    public void verify(String key, String inputCode) {
        validateKey(key);
        validateCode(key, inputCode);
    }

    private void validateKey(String key) {
        if(!verifyCodeRepository.hasKey(key)) {
            throw new VerificationCodeBusinessException(ErrorCode.VERIFICATION_CODE_EXPIRED);
        }
    }

    private void validateCode(String key, String inputCode) {
        String code = verifyCodeRepository.getData(key);
        if(!code.equals(inputCode)) {
            throw new VerificationCodeBusinessException(ErrorCode.VERIFICATION_CODE_INVALID);
        }
    }
}
