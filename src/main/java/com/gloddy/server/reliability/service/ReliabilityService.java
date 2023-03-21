package com.gloddy.server.reliability.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReliabilityService {
    private final ReliabilityQueryHandler reliabilityQueryHandler;

    public Reliability init(User user) {
        return reliabilityQueryHandler.save(new Reliability(user));
    }
}
