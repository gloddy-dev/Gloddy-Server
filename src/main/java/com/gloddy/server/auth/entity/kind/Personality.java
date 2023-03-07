package com.gloddy.server.auth.entity.kind;

import java.util.List;
import java.util.stream.Collectors;

public enum Personality {
    KIND("상냥한"),
    CUTE("귀여운")
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

