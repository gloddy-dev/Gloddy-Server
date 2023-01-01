package com.gloddy.server.estimate.entity.embedded;

public enum PraiseValue {
    CALM,
    KIND,
    ACTIVE,
    HUMOR,
    ABSENCE;

    public boolean isCalm() {
        return this == CALM;
    }

    public boolean isKind() {
        return this == KIND;
    }

    public boolean isActive() {
        return this == ACTIVE;
    }

    public boolean isHumor() {
        return this == HUMOR;
    }

    public boolean isAbsence() {
        return this == ABSENCE;
    }
}
