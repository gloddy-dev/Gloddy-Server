package com.gloddy.server.messaging.sns.publisher;

import com.gloddy.server.messaging.sns.Topic;
import com.gloddy.server.messaging.MessagePublisher;
import com.gloddy.server.messaging.sns.config.SnsProperties;
import io.awspring.cloud.sns.core.SnsTemplate;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SnsPublisher implements MessagePublisher {

    private final SnsProperties snsProperties;
    private final SnsTemplate snsTemplate;

    @Override
    public void publishEvent(Map<String, Object> event, String eventType) {
        String topicProperty = Topic.getTopicProperty(eventType);
        send(snsProperties.getTopic(topicProperty), event, null);
    }

    private void send(String topicName, Map<String, Object> event, @Nullable String subject) {
        log.info("이벤트 발행 시작(AWS SNS)");
        try {
            snsTemplate.sendNotification(topicName, event, subject);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        log.info("이벤트 발행 완료(AWS SNS)");
    }
}
