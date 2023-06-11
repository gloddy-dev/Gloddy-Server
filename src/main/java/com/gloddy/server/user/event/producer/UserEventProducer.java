package com.gloddy.server.user.event.producer;

import com.gloddy.server.core.event.Event;

public interface UserEventProducer {
    void produceEvent(Event event);
}
