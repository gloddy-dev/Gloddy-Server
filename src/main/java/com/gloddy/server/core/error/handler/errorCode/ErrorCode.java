package com.gloddy.server.core.error.handler.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NULL(000, "no content"),

    TOKEN_INVALID(403, "올바르지 않는 토큰입니다."),
    TOKEN_ERROR(403, "토큰 자체에 문제가 있습니다."),
    TOKEN_EMPTY(403, "헤더에 토큰이 존재하지 않습니다."),
    TOKEN_BLANK(403, "토큰이 비어져 있습니다."),
    TOKEN_EXPIRED(403, "토큰이 만료되었습니다."),

    USER_NOT_FOUND(404, "존재하지 않는 유저입니다."),
    ALREADY_USER_SIGN_UP(400, "이미 가입된 유저입니다."),
    INVALID_PHONE_NUMBER(400, "유효하지 않은 전화번호 입니다."),
    PASSWORD_DISCORD(404, "패스워드가 일치하지 않습니다."),

    PRAISE_NOT_FOUND(404, "존재하지 않는 칭찬입니다."),

    RELIABILITY_NOT_FOUND(404, "존재하지 않는 신뢰도 지표입니다."),
    NOT_EXIST_RELIABILITY_LEVEL(404, "존재하지 않는 신뢰도 레벨입니다."),

    GROUP_TIME_INVALID(400, "올바르지 않은 그룹 시작 시간입니다."),
    EXISTS_PARTICIPATING_GROUP(400, "참여 중인 모임이 존재합니다."),
    CANT_ACCEPT_MORE_GROUP_MEMBER(400, "해당 모임의 인원이 모두 다 찼습니다."),
    NOT_FOUND_GROUP_MEMBER(404, "존재하지 않는 그룹 멤버 입니다."),
    GROUP_NOTIFICATION_BATCH_ERROR(500, "그룹 스케줄러 작업이 실패했습니다."),

    EMAIL_INVALID(404, "유효하지 않은 이메일 형식입니다."),
    PHONE_NUMBER_INVALID(400, "유효하지 않은 휴대폰 번호입니다."),
    VERIFICATION_CODE_INVALID(400, "올바르지 않은 인증코드입니다."),
    VERIFICATION_CODE_EXPIRED(400, "인증 코드가 만료되었습니다."),

    ARTICLE_NOT_FOUND(404, "존재하지 않는 게시글입니다."),
    NO_ARTICLE_WRITER(400, "게시글 작성자가 아닙니다."),
    NO_ARTICLE_DELETE_PERMISSION(400, "게시글을 삭제할 권한이 없습니다."),
    NO_NOTICE_ARTICLE_PERMISSION(400, "공지 게시물 생성 권한이 없습니다."),

    COMMENT_NOT_FOUND(404, "존재하지 않는 댓글입니다."),
    COMMENT_USER_MISMATCH(400, "해당 댓글 작성자가 아닙니다."),
    NO_COMMENT_DELETE_PERMISSION(400, "해당 댓글을 삭제할 권한이 없습니다."),
    FILE_EXTENSION_NOT_FOUND(404, "파일 확장자를 얻을 수 없습니다."),
    FILE_INTERNAL_ERROR(500, "파일 byte input stream 변환 중 에러"),
    GROUP_NOT_FOUND(404, "존재하지 않는 그룹입니다."),
    GROUP_NOT_CAPTAIN(403, "권한이 없습니다."),
    APPLY_NOT_FOUND(404, "존재하지 않는 지원서입니다."),
    EXISTS_WAIT_APPLY(400, "대기 상태인 지원서가 존재합니다."),
    AlREADY_PROCESSED_APPLY(400, "이미 처리된 지원서입니다."),
    NO_TOTAL_GROUP_MEMBER_PRAISE(400, "모든 그룹 참여자에 대해 칭찬 하지 않았습니다"),
    ALREADY_EXIST_GROUP_MEMBER(400, "이미 해당 유저는 그룹의 멤버입니다."),

    SMS_BAD_REQUEST(400, "잘못된 형식의 SMS 요청입니다."),
    SMS_UNAUTHORIZED(401, "유효하지 않은 SMS 요청 헤더값입니다."),

    OUTBOX_EVENT_NOT_FOUND(404, "존재하지 않는 이벤트입니다."),

    DATABASE_CONNECTION_FAILED(500, "데이터베이스 연결에 실패했습니다."),
    ;

    private int status;
    private String errorMessage;
}
