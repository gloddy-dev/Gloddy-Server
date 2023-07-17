package com.gloddy.server.authSms.infra.ncloud;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@Component
@RequiredArgsConstructor
public class NcloudSignatureMaker {

    private final NcloudProperties ncloudProperties;

    public String makeSignature(Long time) {
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/"+ ncloudProperties.getServiceId() +"/messages";
        String timestamp = time.toString();
        String accessKey = ncloudProperties.getAccessKey();
        String secretKey = ncloudProperties.getSecretKey();

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        try {
            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            return Base64.encodeBase64String(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
