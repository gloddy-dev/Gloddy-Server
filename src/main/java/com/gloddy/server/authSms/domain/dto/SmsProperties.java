package com.gloddy.server.authSms.domain.dto;


import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class SmsProperties {
    @Value("${naver-cloud-sms.accessKey}")
    private String accessKey;

    @Value("${naver-cloud-sms.secretKey}")
    private String secretKey;

    @Value("${naver-cloud-sms.serviceId}")
    private String serviceId;

    @Value("${naver-cloud-sms.senderPhone}")
    private String phone;
}
