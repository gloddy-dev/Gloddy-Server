package com.gloddy.server.reliability.domain.handler;

import com.gloddy.server.reliability.domain.Reliability;

public interface ReliabilityQueryHandler {
    Reliability findByUserId(Long userId);
    Reliability save(Reliability reliability);
}
