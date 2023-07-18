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

    public void send(SmsRequest.Send request) {
        validateNumber(request.getNumber());
        smsClient.send(request);
    }

    public SmsResponse.VerifyCode verifyCode(SmsRequest.VerifyCode request) {
        validateNumber(request.getNumber());
        verificationCodeUtil.verify(request.getNumber(), request.getCode());
        return new SmsResponse.VerifyCode(true);
    }

    private void validateNumber(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new SmsBusinessException(ErrorCode.PHONE_NUMBER_INVALID);
        }
    }
}
