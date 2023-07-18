package com.gloddy.server.authSms.infra.ncloud;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.SmsBadRequestException;
import com.gloddy.server.core.error.handler.exception.SmsUnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsFeignError implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400 -> throw new SmsBadRequestException(ErrorCode.SMS_BAD_REQUEST);
            case 401 -> throw new SmsUnauthorizedException(ErrorCode.SMS_UNAUTHORIZED);
            default -> throw new RuntimeException(response.reason());
        }
    }
}
