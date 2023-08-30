package com.gloddy.server.myGroup.read;

import com.gloddy.server.group.domain.Group;
import com.gloddy.server.myGroup.read.domainService.NotEstimatedGroupGetExecutor;
import com.gloddy.server.myGroup.read.domainService.ParticipatingGroupGetExecutor;
import com.gloddy.server.myGroup.read.domainService.RejectedGroupGetExecutor;
import com.gloddy.server.myGroup.read.domainService.WaitingGroupGetExecutor;
import com.gloddy.server.myGroup.read.domainService.facade.HostingGroupGetFacade;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import com.gloddy.server.myGroup.read.util.MyGroupDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyGroupReadService {

    private final ParticipatingGroupGetExecutor participatingGroupGetExecutor;
    private final HostingGroupGetFacade hostingGroupGetFacade;
    private final WaitingGroupGetExecutor waitingGroupGetExecutor;
    private final RejectedGroupGetExecutor rejectedGroupGetExecutor;
    private final NotEstimatedGroupGetExecutor notEstimatedGroupGetExecutor;

    @Transactional(readOnly = true)
    public MyGroupResponse.Participating getParticipatingGroups(Long userId) {
        return participatingGroupGetExecutor.getParticipatingGroups(userId);
    }

    @Transactional(readOnly = true)
    public MyGroupResponse.Hosting getHostingGroups(Long userId) {
        return hostingGroupGetFacade.getHostingGroups(userId);
    }

    @Transactional(readOnly = true)
    public MyGroupResponse.Waiting getWaitingGroups(Long userId) {
        return waitingGroupGetExecutor.getWaitingGroups(userId);
    }

    @Transactional(readOnly = true)
    public MyGroupResponse.Rejected getRejectedGroups(Long userId) {
        return rejectedGroupGetExecutor.getRejectedGroups(userId);
    }

    @Transactional(readOnly = true)
    public MyGroupResponse.NotEstimated getNotEstimatedGroups(Long userId) {
        List<Group> groups = notEstimatedGroupGetExecutor.getNotEstimatedGroups(userId);
        return groups.stream()
                .map(it -> MyGroupDtoMapper.mapToNotEstimatedOne(userId, it))
                .collect(Collectors.collectingAndThen(Collectors.toList(), MyGroupResponse.NotEstimated::new));
    }
}
