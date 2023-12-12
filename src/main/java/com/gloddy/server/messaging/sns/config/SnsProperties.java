package com.gloddy.server.messaging.sns.config;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("spring.cloud")
public class SnsProperties {
    private Map<String, String> event;

    public String getTopic(String key) {
        return event.get(key);
    }
}
