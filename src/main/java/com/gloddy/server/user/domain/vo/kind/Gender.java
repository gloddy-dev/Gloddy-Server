package com.gloddy.server.user.domain.vo.kind;

public enum Gender {
    MAIL("남"),
    FEMAIL("여");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}