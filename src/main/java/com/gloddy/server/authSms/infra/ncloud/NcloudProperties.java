package com.gloddy.server.authSms.infra.ncloud;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class NcloudProperties {
    @Value("${cloud.ncloud.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.ncloud.credentials.secret-key}")
    private String secretKey;

    @Value("${ncloud.sms.service-id}")
    private String serviceId;

    @Value("${ncloud.sms.calling-number}")
    private String callingNumber;
}
