package com.gloddy.server.reliability.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import com.gloddy.server.reliability.entity.vo.ScoreMinusType;
import com.gloddy.server.reliability.entity.vo.ScoreType;
import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ScorePlusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReliabilityService {

    private final ReliabilityQueryHandler reliabilityQueryHandler;

    public void update(User user, ScoreType type) {
        Reliability reliability = reliabilityQueryHandler.findByUser(user);
        Long score = updateScore(reliability.getScore(), type);
        ReliabilityLevel level = reliability.getLevel().upgradeLevel(score);

        reliability.updateLevel(level);
        reliability.updateScore(score);
    }

    private Long updateScore(Long originScore, ScoreType type) {
        if (ScorePlusType.isPlusType(type.name())) {
            return plusScore(originScore, type);
        } else if (ScoreMinusType.isMinusType(type.name())) {
            return minusScore(originScore, type);
        } else {
            throw new RuntimeException("존재하지 않는 Score 유형입니다.");
        }
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
