package com.gloddy.server.reliability.handler;

import com.gloddy.server.reliability.entity.Reliability;

public interface ReliabilityQueryHandler {
    Reliability findByUserId(Long userId);
    Reliability save(Reliability reliability);
}
