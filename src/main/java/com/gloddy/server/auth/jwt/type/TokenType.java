package com.gloddy.server.auth.jwt.type;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS(86400L),
    REFRESH(5184000L);

    private final long validTimeSeconds;

    TokenType(long validTimeSeconds) {
        this.validTimeSeconds = validTimeSeconds;
    }

    public boolean isAccess() {
        return this == ACCESS;
    }

    public boolean isRefresh() {
        return this == REFRESH;
    }
}
