package com.gloddy.server.apply.event.producer;

import com.gloddy.server.core.event.Event;

public interface ApplyEventProducer {
    void produceEvent(Event event);
}
