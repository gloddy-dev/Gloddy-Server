package com.gloddy.server.authSms.infra.ncloud;

import com.gloddy.server.authSms.domain.SmsClient;
import com.gloddy.server.authSms.utils.VerificationCodeUtil;
import com.gloddy.server.authSms.domain.dto.SmsRequest;
import com.gloddy.server.authSms.infra.ncloud.dto.NcloudSmsRequest;
import com.gloddy.server.authSms.infra.ncloud.dto.NcloudSmsRequest.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NcloudSmsClient implements SmsClient {

    private final NcloudSmsFeignClient ncloudSmsFeignClient;
    private final NcloudSignatureMaker ncloudSignatureMaker;
    private final NcloudProperties ncloudProperties;
    private final VerificationCodeUtil verificationCodeUtil;

    @Override
    public void send(SmsRequest.Send dto) {
        ncloudSmsFeignClient.send(
                makeHeaders(),
                makeRequest(dto)
        );
    }

    private NcloudSmsRequest makeRequest(SmsRequest.Send dto) {
        return NcloudSmsRequest.builder()
                .type("SMS")
                .from(ncloudProperties.getCallingNumber())
                .content(generateVerificationCode(dto))
                .messages(getMessages(dto))
                .build();
    }

    private HttpHeaders makeHeaders() {
        Long timestamp = System.currentTimeMillis();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", timestamp.toString());
        headers.set("x-ncp-iam-access-key", ncloudProperties.getAccessKey());
        headers.set("x-ncp-apigw-signature-v2", ncloudSignatureMaker.makeSignature(timestamp));
        return headers;
    }

    private List<Message> getMessages(SmsRequest.Send dto) {
        return List.of(new Message(dto.getNumber()));
    }

    private String generateVerificationCode(SmsRequest.Send dto) {
        return verificationCodeUtil.generate(
                dto.getNumber(),
                60 * 3L
        );
    }
}
