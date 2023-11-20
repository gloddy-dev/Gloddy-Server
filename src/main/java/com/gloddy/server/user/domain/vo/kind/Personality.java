package com.gloddy.server.user.domain.vo.kind;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum Personality {
    OUTGOING("외향적인"),
    INTROVERTED("내향적인"),
    CAREFUL("신중한"),
    KIND("친절한"),
    HUMOROUS("유머러스한"),
    OPTIMISTIC("낙천적인"),
    SOCIAL("사교적인"),
    CANDID("솔직한"),
    RESPONSIBLE("책임감있는"),
    TRANQUIL("차분한"),
    ACTIVE("활동적인"),
    SENSIBLE("센스있는"),
    WACKY("엉뚱한"),
    LEADERSHIP("리더십있는")
    ;

    private String value;

    Personality(String value) {
        this.value = value;
    }

    public static List<String> of(List<Personality> personalities) {
       return  personalities.stream()
                .map(Enum::toString)
               .collect(Collectors.toList());
    }
}

