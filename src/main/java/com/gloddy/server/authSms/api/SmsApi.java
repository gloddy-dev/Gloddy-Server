package com.gloddy.server.authSms.api;

import com.gloddy.server.authSms.utils.VerificationCodeUtil;
import com.gloddy.server.authSms.domain.dto.SmsResponse;
import com.gloddy.server.authSms.application.SmsService;
import com.gloddy.server.authSms.domain.dto.SmsRequest;
import com.gloddy.server.core.response.ApiResponse;
import jakarta.validation.Valid;
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

    @PostMapping("/auth/sms")
    public ResponseEntity<Void> send(@RequestBody @Valid SmsRequest.Send request) {
        smsService.send(request);
        return ApiResponse.noContent();
    }

    @PostMapping("/auth/sms/verify-code")
    public ResponseEntity<SmsResponse.VerifyCode> verifyCode(@RequestBody @Valid SmsRequest.VerifyCode request) {
        SmsResponse.VerifyCode response = smsService.verifyCode(request);
        return ApiResponse.ok(response);
    }
}
