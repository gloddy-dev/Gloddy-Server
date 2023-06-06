package com.gloddy.server.praise.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.praise.infra.repository.PraiseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PraiseSaveService {
    private final PraiseJpaRepository praiseJpaRepository;

    @Transactional
    public Praise initPraiseSaveBy(User user) {
        Praise praise = Praise.empty();
        praise.init(user);
        return praiseJpaRepository.save(praise);
    }
}
