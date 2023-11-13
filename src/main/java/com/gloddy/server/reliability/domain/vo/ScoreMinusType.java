package com.gloddy.server.reliability.domain.vo;

import java.util.Arrays;

public enum ScoreMinusType implements ScoreTypes {
    Absence_Group("모임 불참", 10L),
    Leaved_Group("모임 나가기", 5L),
    ;

    private final String description;
    private final Long score;

    private ScoreMinusType(String description, Long score) {
        this.description = description;
        this.score = score;
    }

    public Long minusScore(Long score) {
        long newScore = score - this.score;
        if (newScore > 0) {
            return newScore;
        }
        return 0L;
    }

    public Long getScore() {
        return this.score;
    }

    @Override
    public Long getReflectScore() {
        return -this.score;
    }
}
