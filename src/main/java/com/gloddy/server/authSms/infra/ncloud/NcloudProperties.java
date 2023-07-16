package com.gloddy.server.authSms.infra.ncloud;


import lombok.Value;
import org.springframework.stereotype.Component;

@Component
public class NcloudProperties {
    @Value("${ncloud.credentials.access-key}")
    private String accessKey;

    @Value("${ncloud.credentials.secret-key}")
    private String secretKey;

    @Value("${ncloud.sms.service-id}")
    private String serviceId;

    @Value("${ncloud.sms.calling-number}")
    private String callingNumber;
}
