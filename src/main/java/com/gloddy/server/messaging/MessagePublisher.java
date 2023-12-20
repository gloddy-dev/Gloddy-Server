package com.gloddy.server.messaging;

import java.util.Map;

public interface MessagePublisher {
    void publishEvent(Map<String, Object> event, String eventType);
}
