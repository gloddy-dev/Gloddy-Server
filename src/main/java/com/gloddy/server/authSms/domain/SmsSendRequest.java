package com.gloddy.server.authSms.domain;

import com.gloddy.server.authSms.domain.vo.SmsProvider;
import org.springframework.util.MultiValueMap;

public interface SmsSendRequest {
    SmsProvider smsProvider();
    MultiValueMap<String, String> makeBody();
}
