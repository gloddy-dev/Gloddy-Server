package com.gloddy.server.authSms.infra.api;

import com.gloddy.server.authSms.infra.VerificationCodeService;
import com.gloddy.server.authSms.infra.application.SmsService;
import com.gloddy.server.authSms.infra.dto.SmsRequest;
import com.gloddy.server.authSms.infra.dto.SmsResponse;
import com.gloddy.server.core.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SmsApi {
    private final SmsService smsService;
    private final VerificationCodeService verificationCodeService;

    @PostMapping("/auth/sms")
    public ResponseEntity<Void> send(@RequestBody SmsRequest.Send request) {
        smsService.send(request);
        return ApiResponse.noContent();
    }

    @PostMapping("/auth/sms/verify-code")
    public ResponseEntity<SmsResponse.VerifyCode> verifyCode(SmsRequest.VerifyCode request) {
        SmsResponse.VerifyCode response = verificationCodeService.verify(
                request.getNumber(),
                request.getCode()
        );
        return ApiResponse.ok(response);
    }
}
