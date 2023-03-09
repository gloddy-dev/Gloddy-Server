package com.gloddy.server.reliability.service;

import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;

public interface ReliabilityLevelService {
    ReliabilityLevel upgrade(Long score, ReliabilityLevel level);
}
