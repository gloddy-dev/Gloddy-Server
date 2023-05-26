package com.gloddy.server.auth.domain.vo.kind;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Personality {
    KIND("상냥한"),
    TEST("테스트1"),
    TEST1("테스트2")
    ;

    private String value;

    private Personality(String value) {
        this.value = value;
    }

    public static List<String> of(List<Personality> personalities) {
       return  personalities.stream()
                .map(Enum::toString)
               .collect(Collectors.toList());
    }
}

