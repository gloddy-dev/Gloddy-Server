package com.gloddy.server.reliability.domain.vo;

import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ReliabilityBusinessException;

import java.util.Arrays;

public enum ReliabilityLevel {
    HOOD(90L),
    MATE(180L),
    SOUL_MATE(300L),
    GLODDY(Long.MAX_VALUE),
    ;

    private final Long criterion;

    private ReliabilityLevel (Long criterion) {
        this.criterion = criterion;
    }

    public ReliabilityLevel upgradeLevel(Long score) {
        return Arrays.stream(ReliabilityLevel.values())
                .filter(level -> canUpgradeLevel(level, score))
                .findFirst()
                .orElseThrow(() -> new ReliabilityBusinessException(ErrorCode.NOT_EXIST_RELIABILITY_LEVEL));
    }

    private boolean canUpgradeLevel(ReliabilityLevel level, Long score) {
        return score < level.criterion;
    }
}
