package com.gloddy.server.service.apply;

import com.gloddy.server.apply.application.ApplyService;
import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.apply.domain.handler.ApplyQueryHandler;
import com.gloddy.server.apply.domain.vo.Status;
import com.gloddy.server.apply.exception.CantAcceptMoreGroupMemberException;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.common.myGroup.GroupServiceTest;
import com.gloddy.server.group.domain.Group;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.BDDMockito.*;

public class ApplyStatusUpdateTest extends GroupServiceTest {

    @MockBean
    private ApplyQueryHandler applyQueryHandler;

    @Autowired
    private ApplyService applyService;

    @Test
    @DisplayName("지원서 승인 시 실패합니다 - 그룹의 인원 다 참")
    void updateApplyApproveStatus_fail_by_fullGroup() {

        Long applyId = 99999L;

        //캡틴을 생성합니다.
        User user = Mockito.mock(User.class);
        Long captainId = 9999L;

        willReturn(captainId)
                .given(user).getId();

        //인원이 다 찬 그룹을 생성합니다.
        Group group = Mockito.mock(Group.class);
        willReturn(user)
                .given(group).getCaptain();
        willReturn(false)
                .given(group).canAcceptMoreMembers();

        //승인할 지원서를 생성합니다.
        Apply apply = Mockito.mock(Apply.class);
        willReturn(apply)
                .given(applyQueryHandler).findById(applyId);
        willReturn(group)
                .given(apply).getGroup();

        //when_and_then
        Assertions.assertThatThrownBy(() -> applyService.updateStatusApply(captainId, applyId, Status.APPROVE))
                .isInstanceOf(CantAcceptMoreGroupMemberException.class);
    }
}
