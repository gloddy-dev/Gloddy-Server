package com.gloddy.server.auth.infra.repository.impl;

import com.gloddy.server.auth.domain.VerifyCode;
import com.gloddy.server.auth.domain.handler.VerifyCodeRepository;
import com.gloddy.server.auth.infra.VerifyCodeEntity;
import com.gloddy.server.auth.infra.mapper.VerifyCodeEntityMapper;
import com.gloddy.server.auth.infra.repository.VerifyCodeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static java.time.LocalDateTime.*;

@Repository
@RequiredArgsConstructor
public class IVerifyCodeRepository implements VerifyCodeRepository {

    private final VerifyCodeJpaRepository verifyCodeJpaRepository;

    @Override
    public void setValue(VerifyCode verifyCode) {
        verifyCodeJpaRepository.deleteById(verifyCode.getKey());

        VerifyCodeEntity verifyCodeEntity = VerifyCodeEntityMapper.toEntity(verifyCode);
        verifyCodeJpaRepository.save(verifyCodeEntity);
    }

    @Override
    public String getValue(String key) {
        Optional<VerifyCodeEntity> verifyCodeEntity =
                verifyCodeJpaRepository.findByKeyAndExpireDateAfter(key, now());
        return verifyCodeEntity.map(VerifyCodeEntity::getValue).orElse(null);

    }

    @Override
    public Boolean hasKey(String key) {
        return verifyCodeJpaRepository.existsByKeyAndExpireDateAfter(key, now());
    }
}
