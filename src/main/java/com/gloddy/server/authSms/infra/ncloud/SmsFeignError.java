package com.gloddy.server.authSms.infra.ncloud;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.SmsBusinessException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsFeignError implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> new SmsBusinessException(ErrorCode.SMS_BAD_REQUEST);
            case 401 -> new SmsBusinessException(ErrorCode.SMS_UNAUTHORIZED);
            default -> new RuntimeException(response.reason());
        };
    }
}
