package com.gloddy.server.authSms.infra.ncloud;

import feign.HeaderMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "ncloudSmsFeignClient", url = "${ncloud.sms.base-url}")
//@Headers({
//        "Content-Type: application/json",
//        "x-ncp-apigw-timestamp: {timestamp}",
//        "x-ncp-iam-access-key: {accessKey}",
//        "x-ncp-apigw-signature-v2: {signature}"
//})
public interface NcloudSmsFeignClient {

    @PostMapping("{serviceId}/messages")
    NcloudSmsResponse send(
            @PathVariable("serviceId") String serviceId,
            @RequestHeader Map<String, String> headers,
//            @Param("timestamp") String timestamp,
//            @Param("accessKey") String accessKey,
//            @Param("signature") String signature,
            NcloudSmsRequest request
    );
}
