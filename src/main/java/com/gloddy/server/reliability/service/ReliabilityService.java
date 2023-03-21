package com.gloddy.server.reliability.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.Reliability;

public interface ReliabilityService {
    Reliability init(User user);
}
