package com.gloddy.server.Exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {
    // auth
    FAIL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "인증에 실패했습니다."),
    FAIL_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 형식의 이메일입니다.")
    ;

    private final HttpStatus status;
    private final String description;
}
