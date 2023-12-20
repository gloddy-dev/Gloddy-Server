package com.gloddy.server.batch.group.event.producer;

import com.gloddy.server.core.event.Event;

public interface GroupBatchEventProducer {
    void produceEvent(Event event);
}
