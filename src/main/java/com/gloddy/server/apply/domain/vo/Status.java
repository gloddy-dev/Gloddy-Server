package com.gloddy.server.apply.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    APPROVE("승인"),
    REFUSE("거절"),
    WAIT("대기"),
    ;

    private final String status;

    public boolean isWait() {
        return this == WAIT;
    }

    public boolean isApprove() {
        return this == APPROVE;
    }

    public boolean isRefuse() {
        return this == REFUSE;
    }
}
