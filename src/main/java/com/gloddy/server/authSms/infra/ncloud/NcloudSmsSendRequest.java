package com.gloddy.server.authSms.infra.ncloud;

import com.gloddy.server.authSms.domain.SmsSendRequest;
import com.gloddy.server.authSms.domain.vo.SmsProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NcloudSmsSendRequest implements SmsSendRequest {
    private String toNumber;
    @Override
    public SmsProvider smsProvider() {
        return SmsProvider.NCLOUD;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("to", toNumber);
        return body;
    }
}
