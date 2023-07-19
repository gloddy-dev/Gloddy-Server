package com.gloddy.server.authSms.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class RandomVerificationCodeGenerator implements VerificationCodeGenerator {

    @Override
    public String generate() {
        return RandomStringUtils.randomNumeric(6);
    }
}
