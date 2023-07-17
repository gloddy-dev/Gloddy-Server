package com.gloddy.server.authSms.infra;

import com.gloddy.server.authSms.infra.dto.SmsNumberRequest;

public interface SmsClient {
    void send(SmsNumberRequest dto);
}
