package com.gloddy.server.group.domain.service;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.scrap.infra.repository.ScrapJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupChecker {

    private final ScrapJpaRepository scrapJpaRepository;

    public boolean isMyGroup(User user, Group group) {
        return group.getGroupMemberVOs().existByUserId(user.getId());
    }

    public boolean isGroupCaptain(User user, Group group) {
        return group.getCaptain().equals(user);
    }

    public boolean isScraped(User user, Group group) {
        return scrapJpaRepository.existsByUserAndGroup(user, group);
    }
}
