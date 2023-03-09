package com.gloddy.server.reliability.service;

import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import org.springframework.stereotype.Service;

@Service
public class ReliabilityLevelServiceImpl implements ReliabilityLevelService {

    @Override
    public ReliabilityLevel upgrade(Long score, ReliabilityLevel level) {
        return level.upgradeLevel(score);
    }
}
