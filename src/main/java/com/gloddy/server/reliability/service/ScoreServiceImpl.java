package com.gloddy.server.reliability.service;

import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import com.gloddy.server.reliability.entity.vo.ScorePlusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl {
    private final ReliabilityQueryHandler reliabilityQueryHandler;
    private final ReliabilityService reliabilityLevelService;

    @Transactional
    public void plus(Long userId, ScorePlusType type) {
        Reliability reliability = reliabilityQueryHandler.findByUserId(userId);
        Long score = type.plusScore(reliability.getScore());
        ReliabilityLevel level = reliabilityLevelService.upgrade(score, reliability.getLevel());

        reliabilityQueryHandler.save(new Reliability(
                reliability.getId(),
                userId,
                score,
                level
        ));
    }
}
