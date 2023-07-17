package com.gloddy.server.authSms.infra.api;

import com.gloddy.server.authSms.infra.application.SmsService;
import com.gloddy.server.authSms.infra.dto.SmsNumberRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SmsApi {
    private final SmsService smsService;

    @PostMapping("/auth/sms")
    public void send(@RequestBody SmsNumberRequest request) {
        smsService.send(request);
    }
}
