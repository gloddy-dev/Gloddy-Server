package com.gloddy.server.authSms.infra.ncloud;

import com.gloddy.server.authSms.infra.SmsClient;
import com.gloddy.server.authSms.infra.VerificationCodeService;
import com.gloddy.server.authSms.infra.dto.SmsNumberRequest;
import com.gloddy.server.authSms.infra.ncloud.NcloudSmsRequest.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NcloudSmsClient implements SmsClient {

    private final NcloudSmsFeignClient ncloudSmsFeignClient;
    private final NcloudSignatureMaker ncloudSignatureMaker;
    private final NcloudProperties ncloudProperties;
    private final VerificationCodeService verificationCodeService;

    @Override
    public void send(SmsNumberRequest dto) {
        ncloudSmsFeignClient.send(
                ncloudProperties.getServiceId(),
                makeHeaders(),
                makeRequest(dto)
        );
    }

    private NcloudSmsRequest makeRequest(SmsNumberRequest dto) {
        return NcloudSmsRequest.builder()
                .type("SMS")
                .from(ncloudProperties.getCallingNumber())
                .content(generateVerificationCode(dto))
                .messages(getMessages(dto))
                .build();
    }

    private Map<String, String> makeHeaders() {
        Long timestamp = System.currentTimeMillis();
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.put("x-ncp-apigw-timestamp", timestamp.toString());
        headers.put("x-ncp-iam-access-key", ncloudProperties.getAccessKey());
        headers.put("x-ncp-apigw-signature-v2", ncloudSignatureMaker.makeSignature(timestamp));
        return headers;
    }

    private List<Message> getMessages(SmsNumberRequest dto) {
        return List.of(new Message(dto.getReceivingNumber()));
    }

    private String generateVerificationCode(SmsNumberRequest dto) {
        return verificationCodeService.generate(
                dto.getReceivingNumber(),
                60 * 3L
        );
    }
}
