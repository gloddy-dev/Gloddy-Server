package com.gloddy.server.authSms.application;

import com.gloddy.server.authSms.domain.SmsClient;
import com.gloddy.server.authSms.domain.dto.SmsRequest;
import com.gloddy.server.authSms.domain.dto.SmsResponse;
import com.gloddy.server.authSms.utils.VerificationCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsClient smsClient;
    private final VerificationCodeUtil verificationCodeUtil;

    public void send(SmsRequest.Send request) {
        smsClient.send(request);
    }

    public SmsResponse.VerifyCode verifyCode(SmsRequest.VerifyCode request) {
        return new SmsResponse.VerifyCode(verificationCodeUtil.verify(
                request.getNumber(),
                request.getCode()
        ));
    }
}
