package com.gloddy.server.batch.messaging.producer;

import com.gloddy.server.core.event.Event;

public interface MessagingBatchEventProducer {
    void produceEvent(Event event);
}
