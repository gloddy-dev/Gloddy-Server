package com.gloddy.server.reliability.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.handler.ReliabilityQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReliabilitySaveService {

    private final ReliabilityQueryHandler reliabilityQueryHandler;

    public Reliability init(User user) {
        return reliabilityQueryHandler.save(new Reliability(user));
    }
}
