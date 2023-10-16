package com.gloddy.server.auth.domain.handler;

import com.gloddy.server.auth.domain.VerifyCode;

public interface VerifyCodeRepository {

    void setData(VerifyCode verifyCode);

    String getData(String key);

    Boolean hasKey(String key);
}
