package com.gloddy.server.reliability.entity.vo;

import java.util.Arrays;

public enum ScorePlusType {
    Estimated("평가 참여", 5L),
    Praised("칭찬 스티커 (지목 받은 사용자)", 2L),
    Mated("최고의 짝꿍 (지목 받은 인원 사용자)", 1L),
    Created_Group("모임 생성", 2L),
    ;

    private final String description;
    private final Long score;

    private ScorePlusType(String description, Long score) {
        this.description = description;
        this.score = score;
    }

    public static boolean isPlusType(String scoreType) {
        return Arrays.stream(ScorePlusType.values())
                .anyMatch(type -> type.name().equals(scoreType));
    }

    public Long plusScore(Long score) {
        return score + this.score;
    }
}
