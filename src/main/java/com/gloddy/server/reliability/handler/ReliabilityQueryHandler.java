package com.gloddy.server.reliability.handler;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.Reliability;

public interface ReliabilityQueryHandler {
    Reliability findByUser(User user);
    Reliability save(Reliability reliability);
}
