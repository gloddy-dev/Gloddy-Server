package com.gloddy.server.outbox.event.producer;


import com.gloddy.server.core.event.Event;

public interface OutboxEventProducer {
    void produceEvent(Event payload);
}
