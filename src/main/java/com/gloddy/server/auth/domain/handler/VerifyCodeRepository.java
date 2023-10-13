package com.gloddy.server.auth.domain.handler;

import com.gloddy.server.auth.domain.VerifyCode;

public interface VerifyCodeRepository {

    void setValue(VerifyCode verifyCode);

    String getValue(String key);

    Boolean hasKey(String key);
}
