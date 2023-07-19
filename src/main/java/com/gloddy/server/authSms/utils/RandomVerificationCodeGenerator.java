package com.gloddy.server.authSms.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class RandomVerificationCodeGenerator implements VerificationCodeGenerator {

    public final static int CODE_LENGTH = 6;

    @Override
    public String generate() {
        return RandomStringUtils.randomNumeric(CODE_LENGTH);
    }
}
