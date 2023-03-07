package com.gloddy.server.scrap.service;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.user.handler.UserQueryHandler;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.dto.GroupResponse.GetGroup;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.handler.GroupHandler;
import com.gloddy.server.group.service.GroupUserCountService;
import com.gloddy.server.scrap.dto.ScrapResponse.CreateScrap;
import com.gloddy.server.scrap.entity.Scrap;
import com.gloddy.server.scrap.repository.ScrapJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapJpaRepository scrapJpaRepository;
    private final GroupHandler groupHandler;
    private final UserQueryHandler userHandler;
    private final GroupUserCountService groupUserCountService;

    public CreateScrap create(Long groupId, Long userId) {
        Group group = groupHandler.findById(groupId);
        User user = userHandler.findById(userId);
        Scrap scrap = scrapJpaRepository.save(new Scrap(user, group));
        return new CreateScrap(scrap.getId());
    }

    @Transactional(readOnly = true)
    public PageResponse<GetGroup> findScrapByUserId(Long userId, PageRequest pageRequest) {
        User user = userHandler.findById(userId);
        Page<GetGroup> scraps = scrapJpaRepository.findByUser(user, pageRequest)
                .map(this::generateGroupDto);
        return PageResponse.from(scraps);
    }

    private GetGroup generateGroupDto(Scrap scrap) {
        Group group = scrap.getGroup();
        return new GetGroup(
                group.getId(),
                group.getFileUrl(),
                group.getTitle(),
                group.getContent(),
                Math.toIntExact(groupUserCountService.countUserInGroup(group.getId())),
                group.getPlace(),
                group.getMeetDate()
        );
    }

    @Transactional
    public void delete(Long groupId, Long userId) {
        Group group = groupHandler.findById(groupId);
        User user = userHandler.findById(userId);
        scrapJpaRepository.deleteByUserAndGroup(user, group);
    }
}
