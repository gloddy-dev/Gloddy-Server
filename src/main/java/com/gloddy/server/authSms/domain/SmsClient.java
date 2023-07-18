package com.gloddy.server.authSms;

import com.gloddy.server.authSms.dto.SmsRequest;

public interface SmsClient {
    void send(SmsRequest.Send dto);
}
