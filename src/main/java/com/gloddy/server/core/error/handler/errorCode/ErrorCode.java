package com.gloddy.server.core.error.handler.errorCode;

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

    USER_NOT_FOUND(404, "존재하지 않는 유저입니다."),
    PASSWORD_DISCORD(404, "패스워드가 일치하지 않습니다."),

    EMAIL_INVALID(404, "유효하지 않은 이메일 형식입니다."),
    CODE_INVALID(400, "올바르지 않은 인증코드입니다."),

    ARTICLE_NOT_FOUND(404, "존재하지 않는 게시글입니다."),

    COMMENT_NOT_FOUND(404, "존재하지 않는 댓글입니다."),
    COMMENT_USER_MISMATCH(400, "해당 댓글 작성자가 아닙니다."),
    FILE_EXTENSION_NOT_FOUND(404, "파일 확장자를 얻을 수 없습니다."),
    FILE_INTERNAL_ERROR(500, "파일 byte input stream 변환 중 에러"),
    GROUP_NOT_FOUND(400, "존재하지 않는 모입입니다")
    ;

    private int status;
    private String errorMessage;
}
