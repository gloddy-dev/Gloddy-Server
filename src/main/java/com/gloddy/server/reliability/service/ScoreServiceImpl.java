package com.gloddy.server.reliability.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.vo.ScoreMinusType;
import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ScorePlusType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl implements ScoreService {
    private final ReliabilityQueryHandler reliabilityQueryHandler;

    @Transactional
    public Long update(User user, String scoreType) {
        Reliability reliability = reliabilityQueryHandler.findByUser(user);
        return updateScore(reliability.getScore(), scoreType);
    }

    private Long updateScore(Long originScore, String scoreType) {
        if (ScorePlusType.isPlusType(scoreType)) {
            return plusScore(originScore, scoreType);
        }
        return minusScore(originScore, scoreType);
    }

    private Long plusScore(Long originScore, String scoreType) {
        ScorePlusType type = ScorePlusType.valueOf(scoreType);
        return type.plusScore(originScore);
    }

    private Long minusScore(Long originScore, String scoreType) {
        ScoreMinusType type = ScoreMinusType.valueOf(scoreType);
        return type.minusScore(originScore);
    }
}
