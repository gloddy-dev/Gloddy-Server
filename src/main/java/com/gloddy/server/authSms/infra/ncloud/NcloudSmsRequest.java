package com.gloddy.server.authSms.infra.ncloud;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class NcloudSmsRequest {
    private String type;
    private String from;
    private String content;
    private List<Message> messages;

    @Getter
    @AllArgsConstructor
    public static class Message {
        private String to;
    }
}
