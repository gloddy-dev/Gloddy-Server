package com.gloddy.server.authSms.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomVerificationCodeGenerator implements VerificationCodeGenerator {

    @Override
    public String generate() {
        Random random = new Random();
        return String.valueOf(random.nextInt(999999));
    }
}
