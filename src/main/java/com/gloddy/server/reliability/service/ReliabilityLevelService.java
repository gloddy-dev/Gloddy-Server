package com.gloddy.server.reliability.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReliabilityLevelService {
    private final ReliabilityQueryHandler reliabilityQueryHandler;

    public void update(User user, Long score) {
        Reliability reliability = reliabilityQueryHandler.findByUser(user);
        ReliabilityLevel level = reliability.getLevel().upgradeLevel(score);

        reliabilityQueryHandler.save(new Reliability(
                reliability.getId(),
                reliability.getUser(),
                score,
                level
        ));
    }
}
