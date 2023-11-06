package com.gloddy.server.messaging.sns.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.cloud.event")
@Getter
@Setter
public class SnsProperties {
    private String applyTopic;
    private String groupMemberTopic;
}
