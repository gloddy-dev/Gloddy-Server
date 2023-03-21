package com.gloddy.server.reliability.handler;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ReliabilityBusinessException;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.repository.ReliabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReliabilityQueryHandlerImpl implements ReliabilityQueryHandler {
    private final ReliabilityRepository reliabilityRepository;

    @Override
    public Reliability findByUser(User user) {
        return reliabilityRepository.findByUser(user)
                .orElseThrow(() -> new ReliabilityBusinessException(ErrorCode.RELIABILITY_NOT_FOUND));
    }

    @Override
    public Reliability save(Reliability reliability) {
        return reliabilityRepository.save(reliability);
    }
}
