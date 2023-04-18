package com.gloddy.server.reliability.entity.vo;

import java.util.Arrays;

public enum ScoreMinusType {
    Absence_Group("모임 불참", 10L),
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
}
