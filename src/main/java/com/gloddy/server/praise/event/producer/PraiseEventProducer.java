package com.gloddy.server.praise.event.producer;

import com.gloddy.server.core.event.Event;

public interface PraiseEventProducer {
    void produceEvent(Event event);
}
