package com.gloddy.server.reliability.service;

import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import org.springframework.stereotype.Service;

@Service
public class ReliabilityServiceImpl implements ReliabilityService {

    @Override
    public ReliabilityLevel updateLevel(Long score, ReliabilityLevel level) {
        return level.upgradeLevel(score);
    }
}
