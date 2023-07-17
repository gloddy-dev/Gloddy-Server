package com.gloddy.server.authSms.infra.application;

import com.gloddy.server.authSms.infra.SmsClient;
import com.gloddy.server.authSms.infra.VerificationCodeService;
import com.gloddy.server.authSms.infra.dto.SmsNumberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsClient smsClient;

    public void send(SmsNumberRequest request) {
        smsClient.send(request);
    }
}
