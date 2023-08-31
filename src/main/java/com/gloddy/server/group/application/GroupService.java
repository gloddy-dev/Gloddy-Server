package com.gloddy.server.group.application;

import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.service.ApplyGetExecutor;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.group.domain.handler.GroupCommandHandler;
import com.gloddy.server.group.domain.handler.GroupQueryHandler;
import com.gloddy.server.group.domain.service.GroupChecker;
import com.gloddy.server.group.domain.service.GroupDtoMapper;
import com.gloddy.server.group.domain.service.GroupFactory;
import com.gloddy.server.group.event.producer.GroupEventProducer;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.dto.GroupResponse;
import com.gloddy.server.group.domain.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupQueryHandler groupQueryHandler;
    private final GroupCommandHandler groupCommandHandler;
    private final UserQueryHandler userQueryHandler;
    private final GroupEventProducer groupEventProducer;
    private final ApplyGetExecutor applyGetExecutor;
    private final GroupFactory groupFactory;
    private final GroupChecker groupChecker;

    @Transactional(readOnly = true)
    public PageResponse<GroupResponse.GetGroup> getGroups(int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Group> groupPage = groupQueryHandler.findGroupPage(pageable);
        Page<GroupResponse.GetGroup> getGroupPage = GroupDtoMapper.mapToGetGroupPageFrom(groupPage);
        return PageResponse.from(getGroupPage);
    }

    @Transactional
    public GroupResponse.Create createGroup(Long captainId, GroupRequest.Create req) {
        User captain = userQueryHandler.findById(captainId);

        Group newGroup = captain.saveGroup(groupFactory, groupEventProducer, groupCommandHandler, req);
        return new GroupResponse.Create(newGroup.getId());
    }

    @Transactional(readOnly = true)
    public GroupResponse.GetGroupDetail getGroupDetail(Long userId, Long groupId) {
        User user = userQueryHandler.findById(userId);
        Group group = groupQueryHandler.findById(groupId);

        return GroupDtoMapper.mapToGetGroupDetailFrom(user, group, getApplyStatus(userId, groupId), groupChecker);
    }

    private Status getApplyStatus(Long userId, Long groupId) {
        return applyGetExecutor.getStatusByGroupAndUser(groupId, userId);
    }
}
