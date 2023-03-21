package com.gloddy.server.reliability.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.vo.ScoreMinusType;
import com.gloddy.server.reliability.entity.vo.ScoreType;
import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ScorePlusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ReliabilityQueryHandler reliabilityQueryHandler;

    @Transactional
    public Long update(User user, ScoreType type) {
        Reliability reliability = reliabilityQueryHandler.findByUser(user);
        return updateScore(reliability.getScore(), type);
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
