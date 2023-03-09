package com.gloddy.server.reliability.service;

import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import com.gloddy.server.reliability.entity.vo.ScoreMinusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScoreMinusService {
    private final ReliabilityQueryHandler reliabilityQueryHandler;
    private final ReliabilityLevelService reliabilityLevelService;

    @Transactional
    public void minus(Long userId, ScoreMinusType type) {
        Reliability reliability = reliabilityQueryHandler.findByUserId(userId);
        Long score = type.minusScore(reliability.getScore());
        ReliabilityLevel level = reliabilityLevelService.upgrade(score, reliability.getLevel());

        reliabilityQueryHandler.save(new Reliability(
                reliability.getId(),
                userId,
                score,
                level
        ));
    }
}
