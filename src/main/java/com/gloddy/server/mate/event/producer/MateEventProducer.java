package com.gloddy.server.mate.event.producer;

import com.gloddy.server.core.event.Event;

public interface MateEventProducer {
    void produceEvent(Event event);
}
