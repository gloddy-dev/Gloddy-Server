package com.gloddy.server.estimate.application.praise;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.estimate.domain.Praise;
import com.gloddy.server.estimate.infra.repository.PraiseJpaRepository;
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
