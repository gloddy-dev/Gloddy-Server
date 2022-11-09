package com.gloddy.server.auth.entity.score;

import lombok.Getter;

@Getter
public enum Score {
    BEST_MATE(2);

    private int value;

    Score(int value) {
        this.value = value;
    }
}
