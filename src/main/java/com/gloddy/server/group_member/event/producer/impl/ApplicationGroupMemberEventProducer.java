package com.gloddy.server.group_member.event.producer.impl;

import com.gloddy.server.core.event.Event;
import com.gloddy.server.group_member.event.producer.GroupMemberEventProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationGroupMemberEventProducer implements GroupMemberEventProducer {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void produceEvent(Event event) {
        eventPublisher.publishEvent(event);
    }
}
