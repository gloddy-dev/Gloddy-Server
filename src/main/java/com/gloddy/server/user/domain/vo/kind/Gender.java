package com.gloddy.server.user.domain.vo.kind;

public enum Gender {
    MALE("남"),
    FEMALE("여");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
