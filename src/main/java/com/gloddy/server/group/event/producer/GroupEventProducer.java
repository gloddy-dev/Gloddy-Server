package com.gloddy.server.group.event.producer;

import com.gloddy.server.core.event.Event;

public interface GroupEventProducer {
    void produceEvent(Event event);
}
