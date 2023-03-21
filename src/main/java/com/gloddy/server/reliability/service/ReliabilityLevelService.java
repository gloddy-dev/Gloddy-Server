package com.gloddy.server.reliability.service;

import com.gloddy.server.auth.entity.User;

public interface ReliabilityLevelService {
    void update(User user, Long score);
}
