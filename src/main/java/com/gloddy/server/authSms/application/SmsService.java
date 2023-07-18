package com.gloddy.server.authSms.infra.application;

import com.gloddy.server.authSms.infra.SmsClient;
import com.gloddy.server.authSms.infra.dto.SmsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsClient smsClient;

    public void send(SmsRequest.Send request) {
        smsClient.send(request);
    }
}
