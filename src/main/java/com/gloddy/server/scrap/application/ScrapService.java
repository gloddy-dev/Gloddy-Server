package com.gloddy.server.scrap.application;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.domain.dto.GroupResponse.GetGroup;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.handler.GroupHandler;
import com.gloddy.server.group.application.GroupUserCountService;
import com.gloddy.server.scrap.domain.dto.ScrapResponse.CreateScrap;
import com.gloddy.server.scrap.domain.Scrap;
import com.gloddy.server.scrap.infra.repository.ScrapJpaRepository;
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
                group.getImageUrl(),
                group.getTitle(),
                group.getContent(),
                group.getMemberCount(),
                group.getMaxUser(),
                group.getPlace().getName(),
                group.getPlace().getAddress(),
                group.getMeetDate().toString(),
                group.getStartDateTime().toLocalTime().toString(),
                group.getEndDateTime().toLocalTime().toString()
        );
    }

    @Transactional
    public void delete(Long groupId, Long userId) {
        Group group = groupHandler.findById(groupId);
        User user = userHandler.findById(userId);
        scrapJpaRepository.deleteByUserAndGroup(user, group);
    }
}
