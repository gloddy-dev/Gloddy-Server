package com.gloddy.server.reliability.application;

import com.gloddy.server.reliability.domain.vo.ReliabilityLevel;
import com.gloddy.server.reliability.domain.vo.ScoreMinusType;
import com.gloddy.server.reliability.domain.vo.ScoreType;
import com.gloddy.server.reliability.domain.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.vo.ScorePlusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReliabilityService {

    private final ReliabilityQueryHandler reliabilityQueryHandler;

    public void update(Long userId, ScoreType type) {
        Reliability reliability = reliabilityQueryHandler.findByUserId(userId);
        Long score = updateScore(reliability.getScore(), type);
        ReliabilityLevel level = reliability.getLevel().upgradeLevel(score);

        reliability.updateLevel(level);
        reliability.updateScore(score);
    }

    private Long updateScore(Long originScore, ScoreType type) {
        if (ScorePlusType.isPlusType(type.name())) {
            return plusScore(originScore, type);
        }
        return minusScore(originScore, type);
    }

    private Long plusScore(Long originScore, ScoreType scoreType) {
        ScorePlusType type = ScorePlusType.valueOf(scoreType.name());
        return type.plusScore(originScore);
    }

    private Long minusScore(Long originScore, ScoreType scoreType) {
        ScoreMinusType type = ScoreMinusType.valueOf(scoreType.name());
        return type.minusScore(originScore);
    }
}
