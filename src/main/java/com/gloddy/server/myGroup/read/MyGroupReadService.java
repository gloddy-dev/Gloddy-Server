package com.gloddy.server.myGroup.read;

import com.gloddy.server.myGroup.read.domainService.ParticipatingGroupGetExecutor;
import com.gloddy.server.myGroup.read.domainService.facade.HostingGroupGetFacade;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyGroupReadService {

    private final ParticipatingGroupGetExecutor participatingGroupGetExecutor;
    private final HostingGroupGetFacade hostingGroupGetFacade;

    @Transactional(readOnly = true)
    public MyGroupResponse.Participating getParticipatingGroups(Long userId) {
        return participatingGroupGetExecutor.getParticipatingGroups(userId);
    }

    @Transactional(readOnly = true)
    public MyGroupResponse.Hosting getHostingGroups(Long userId) {
        return hostingGroupGetFacade.getHostingGroups(userId);
    }
}
