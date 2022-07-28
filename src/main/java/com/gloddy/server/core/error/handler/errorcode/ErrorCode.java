package com.gloddy.server.core.error.handler.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NULL(000, "no content"),

    TOKEN_INVALID(400, "올바르지 않는 토큰입니다."),
    TOKEN_ERROR(400, "토큰 자체에 문제가 있습니다."),
    TOKEN_EMPTY(400, "헤더에 토큰이 존재하지 않습니다."),
    TOKEN_BLANK(400, "토큰이 비어져 있습니다."),
    TOKEN_EXPIRED(412, "토큰이 만료되었습니다."),

    USER_NOT_FOUND(404, "존재하지 않는 유저입니다.");

    private int status;
    private String errorMessage;
}
