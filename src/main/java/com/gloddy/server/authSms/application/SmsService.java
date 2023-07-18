package com.gloddy.server.authSms.application;

import com.gloddy.server.authSms.domain.SmsClient;
import com.gloddy.server.authSms.domain.dto.SmsRequest;
import com.gloddy.server.authSms.domain.dto.SmsResponse;
import com.gloddy.server.authSms.utils.VerificationCodeUtil;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.SmsBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsClient smsClient;
    private final VerificationCodeUtil verificationCodeUtil;
    public static int PHONE_NUMBER_LENGTH = 11;

    public void send(SmsRequest.Send request) {
        smsClient.send(request);
    }

    public SmsResponse.VerifyCode verifyCode(SmsRequest.VerifyCode request) {
        verificationCodeUtil.verify(request.getNumber(), request.getCode());
        return new SmsResponse.VerifyCode(true);
    }
}
