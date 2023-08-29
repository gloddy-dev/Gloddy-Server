package com.gloddy.server.common.myGroup;

import com.gloddy.server.apply.application.ApplyService;
import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.common.BaseServiceTest;
import com.gloddy.server.group.application.GroupService;
import com.gloddy.server.group.domain.dto.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public abstract class MyGroupServiceTest extends BaseServiceTest {

    @Autowired
    protected GroupService groupService;

    @Autowired
    protected ApplyService applyService;

    protected GroupRequest.Create createGroupCreateCommand(LocalDate meetDate, String startTime, String endTime) {
        return new GroupRequest.Create(
                "imageUrl",
                "title",
                "content",
                meetDate,
                startTime,
                endTime,
                "placeName",
                "placeAddress",
                "130",
                "25",
                10
        );
    }

    protected ApplyRequest.Create createApplyCreateCommand() {
        return new ApplyRequest.Create(
                "introduce",
                "reason"
        );
    }

    protected Long createGroup(Long captainId, GroupRequest.Create command) {
        return groupService.createGroup(captainId, command).getGroupId();
    }

    protected Long createApply(Long userId, Long groupId, ApplyRequest.Create command) {
        return applyService.createApply(userId, groupId, command).getApplyId();
    }

    protected void approveApply(Long captainId, Long applyId) {
        applyService.updateStatusApply(captainId, null, applyId, Status.APPROVE);
    }
}
