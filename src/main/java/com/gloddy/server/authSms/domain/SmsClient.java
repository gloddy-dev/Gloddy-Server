package com.gloddy.server.authSms.domain;

import com.gloddy.server.authSms.domain.dto.SmsRequest;

public interface SmsClient {
    void send(SmsRequest.Send dto);
}
