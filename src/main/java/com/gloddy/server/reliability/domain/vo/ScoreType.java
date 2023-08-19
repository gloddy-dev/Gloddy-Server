package com.gloddy.server.reliability.domain.vo;

public enum ScoreType {
    Estimated("평가 참여"),
    Praised("칭찬 스티커 (지목 받은 사용자)"),
    Mated("최고의 짝꿍 (지목 받은 인원 사용자)"),
    Created_Group("모임 생성"),
    Absence_Group("모임 불참"),
    Leaved_Group("모임 나가기")
    ;

    private final String description;

    private ScoreType(String description) {
        this.description = description;
    }
}
