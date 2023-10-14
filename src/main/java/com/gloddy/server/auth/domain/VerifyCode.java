package com.gloddy.server.auth.domain;

import java.time.LocalDateTime;

public class VerifyCode {

    private String key;
    private String value;
    private LocalDateTime expireDate;

    protected VerifyCode(){

    }

    public VerifyCode(String key, String value, long duration) {
        this.key = key;
        this.value = value;
        this.expireDate = LocalDateTime.now().plusSeconds(duration);
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public LocalDateTime getExpireDate() {
        return this.expireDate;
    }
}
