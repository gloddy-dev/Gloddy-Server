package com.gloddy.server.group.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.event.score.ScoreEventPublisher;
import com.gloddy.server.core.event.score.ScoreUpdateEvent;
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
    private final ScoreEventPublisher scoreEventPublisher;

    @Transactional
    public void completePraise(Long userId, Long groupId) {
        UserGroup findUserGroup = userGroupJpaRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(() -> new RuntimeException("not found UserGroup"));
        findUserGroup.completePraise();
        scoreEventPublisher.publish(new ScoreUpdateEvent(findUserGroup.getUser(), ScoreType.Estimated));
    }
}
