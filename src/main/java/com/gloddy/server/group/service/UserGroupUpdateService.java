package com.gloddy.server.group.service;

import com.gloddy.server.core.event.reliability.ReliabilityEventPublisher;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.repository.UserGroupJpaRepository;
import com.gloddy.server.reliability.entity.vo.ScoreType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserGroupUpdateService {
    private final UserGroupJpaRepository userGroupJpaRepository;
    private final ReliabilityEventPublisher reliabilityEventPublisher;

    @Transactional
    public void completePraise(Long userId, Long groupId) {
        UserGroup findUserGroup = userGroupJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("not found UserGroup"));
        findUserGroup.completePraise();
        reliabilityEventPublisher.publish(new ReliabilityScoreUpdateEvent(findUserGroup.getUser(), ScoreType.Estimated));
    }
}
