package com.gloddy.server.auth.infra.repository;

import com.gloddy.server.auth.infra.VerifyCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface VerifyCodeJpaRepository extends JpaRepository<VerifyCodeEntity, String> {
    Optional<VerifyCodeEntity> findByKeyAndExpireDateAfter(String key, LocalDateTime time);

    Boolean existsByKeyAndExpireDateAfter(String key, LocalDateTime time);
}
