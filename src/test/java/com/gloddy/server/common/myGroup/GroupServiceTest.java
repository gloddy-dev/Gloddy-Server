package com.gloddy.server.common.myGroup;

import com.gloddy.server.apply.application.ApplyService;
import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.common.BaseServiceTest;
import com.gloddy.server.group.application.GroupService;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group_member.application.GroupMemberService;
import com.gloddy.server.group_member.domain.dto.GroupMemberRequest;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mockStatic;

public abstract class GroupServiceTest extends BaseServiceTest {

    @Autowired
    protected GroupService groupService;

    @Autowired
    protected ApplyService applyService;

    @Autowired
    protected GroupMemberService groupMemberService;

    protected GroupRequest.Create createGroupCreateCommand(LocalDate meetDate, String startTime) {
        return new GroupRequest.Create(
                "imageUrl",
                "title",
                "content",
                meetDate,
                startTime,
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

    protected void refuseApply(Long captainId, Long applyId) {
        applyService.updateStatusApply(captainId, null, applyId, Status.REFUSE);
    }

    protected void checkRejectedApply(Long userId, Long applyId) {
        applyService.checkRejectedApply(userId, applyId);
    }

    protected GroupMemberRequest.Estimate createEstimateCommand(List<Long> praisedUserId, Long matedUserId) {

        List<GroupMemberRequest.Estimate.PraiseInfo> praiseInfos = praisedUserId.stream()
                .map(it -> new GroupMemberRequest.Estimate.PraiseInfo(it, PraiseValue.KIND))
                .toList();
        GroupMemberRequest.Estimate.MateInfo mateInfo = new GroupMemberRequest.Estimate.MateInfo(matedUserId, "reason");

        return new GroupMemberRequest.Estimate(praiseInfos, mateInfo);
    }

    protected void mockLocalDateTimeToOneDayAfterGroupStartDateTime() {
        LocalDateTime twoDaysLater = LocalDateTime.now().plusDays(2);
        try (MockedStatic<LocalDateTime> localDateTimeMock = mockStatic(LocalDateTime.class)) {
            // 2일 뒤의 시간을 mock
            localDateTimeMock.when(LocalDateTime::now).thenReturn(twoDaysLater);
        }
    }

    protected void mockLocalDateTimeToTwoDayAfterGroupStartDateTime() {
        LocalDateTime threeDaysLater = LocalDateTime.now().plusDays(3);
        try (MockedStatic<LocalDateTime> localDateTimeMock = mockStatic(LocalDateTime.class)) {
            // 3일 뒤의 시간을 mock
            localDateTimeMock.when(LocalDateTime::now).thenReturn(threeDaysLater);
        }
    }

}
