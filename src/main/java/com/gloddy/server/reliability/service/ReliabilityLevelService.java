package com.gloddy.server.reliability.service;

import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;

public interface ReliabilityService {
    ReliabilityLevel updateLevel(Long score, ReliabilityLevel level);
}
