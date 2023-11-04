package com.gloddy.server.messaging.sns.publisher;

import com.gloddy.server.messaging.AdapterEvent;
import com.gloddy.server.messaging.MessagePublisher;
import com.gloddy.server.messaging.adapter.apply.event.ApplyAdapterEvent;
import com.gloddy.server.messaging.adapter.group.event.GroupMemberAdapterEvent;
import com.gloddy.server.messaging.sns.config.SnsProperties;
import io.awspring.cloud.sns.core.SnsTemplate;
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
    public void publishApplyEvent(ApplyAdapterEvent event) {
        send(snsProperties.getApplyTopic(), event, null);
    }

    @Override
    public void publishGroupMemberEvent(GroupMemberAdapterEvent event) {
        send(snsProperties.getGroupMemberTopic(), event, null);
    }

    private void send(String topicName, AdapterEvent event, @Nullable String subject) {
        log.info("이벤트 발행 시작(AWS SNS)");
        try {
            snsTemplate.sendNotification(topicName, event, subject);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("이벤트 발행 완료(AWS SNS)");
    }
}
