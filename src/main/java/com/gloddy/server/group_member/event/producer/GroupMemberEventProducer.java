package com.gloddy.server.group_member.event.producer;

import com.gloddy.server.core.event.Event;

public interface GroupMemberEventProducer {
    void produceEvent(Event event);
}
