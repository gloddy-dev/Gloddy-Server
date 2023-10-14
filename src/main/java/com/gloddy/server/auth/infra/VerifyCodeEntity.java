package com.gloddy.server.auth.infra;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "verify_code")
public class VerifyCodeEntity {
    @Id
    @Column(name = "`key`")
    private String key;

    @Column(name = "`value`")
    private String value;

    private LocalDateTime expireDate;
}
