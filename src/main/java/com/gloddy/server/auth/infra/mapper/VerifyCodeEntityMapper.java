package com.gloddy.server.auth.infra.mapper;

import com.gloddy.server.auth.domain.VerifyCode;
import com.gloddy.server.auth.infra.VerifyCodeEntity;

public class VerifyCodeEntityMapper {

    public static VerifyCodeEntity toEntity(VerifyCode verifyCode) {
        return new VerifyCodeEntity(
                verifyCode.getKey(),
                verifyCode.getValue(),
                verifyCode.getExpireDate()
        );
    }
}
