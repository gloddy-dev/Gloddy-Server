package com.gloddy.server.reliability.service;

import com.gloddy.server.auth.entity.User;

public interface ScoreService {
    Long update(User user, String scoreType);
}
