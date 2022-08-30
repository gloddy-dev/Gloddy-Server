package com.gloddy.server.apply.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    APPROVE("승인"),
    REFUSE("거절"),
    ;

    private final String status;
}
