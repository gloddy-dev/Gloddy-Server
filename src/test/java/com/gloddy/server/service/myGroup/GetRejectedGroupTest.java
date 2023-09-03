package com.gloddy.server.service.myGroup;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.common.myGroup.GroupServiceTest;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.myGroup.read.MyGroupReadService;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class GetRejectedGroupTest extends GroupServiceTest {

    @Autowired
    private MyGroupReadService myGroupReadService;

    @Nested
    class Case1 {

        private Long targetUserId;
        private Long groupId1;
        private Long groupId2;
        private Long applyId1;
        private Long applyId2;

        @Test
        @Commit
        void getAllRejectedGroups_returns_all() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand1 = createGroupCreateCommand(
                    LocalDate.now().plusDays(1),
                    "12:00",
                    "13:00");
            GroupRequest.Create groupCreateCommand2 = createGroupCreateCommand(
                    LocalDate.now().plusDays(2),
                    "12:00",
                    "13:00");
            groupId1 = createGroup(captainId, groupCreateCommand1);
            groupId2 = createGroup(captainId, groupCreateCommand2);

            Long applierId = createUser();
            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            applyId1 = createApply(applierId, groupId1, applyCreateCommand);
            applyId2 = createApply(applierId, groupId2, applyCreateCommand);

            refuseApply(captainId, applyId1);
            refuseApply(captainId, applyId2);

            targetUserId = applierId;
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            MyGroupResponse.Rejected rejectedGroups = myGroupReadService.getRejectedGroups(targetUserId);

            //then
            assertThat(rejectedGroups.getGroups()).hasSize(2);
            assertThat(rejectedGroups.getGroups().get(0).getApplyId()).isEqualTo(applyId2);
            assertThat(rejectedGroups.getGroups().get(0).getGroup().getGroupId()).isEqualTo(groupId2);
            assertThat(rejectedGroups.getGroups().get(1).getApplyId()).isEqualTo(applyId1);
            assertThat(rejectedGroups.getGroups().get(1).getGroup().getGroupId()).isEqualTo(groupId1);

            //after
            clear();
        }
    }

    @Nested
    class Case2 {

        private Long targetUserId;

        @Test
        @Commit
        void getAllRejectedGroups_returns_empty_list_when_all_refused_check() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand = createGroupCreateCommand(
                    LocalDate.now().plusDays(1),
                    "12:00",
                    "13:00");
            Long groupId = createGroup(captainId, groupCreateCommand);

            Long applierId = createUser();
            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            Long applyId = createApply(applierId, groupId, applyCreateCommand);

            refuseApply(captainId, applyId);

            checkRejectedApply(applierId, applyId);

            targetUserId = applierId;
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            MyGroupResponse.Rejected rejectedGroups = myGroupReadService.getRejectedGroups(targetUserId);

            //then
            assertThat(rejectedGroups.getGroups()).isEmpty();

            //after
            clear();
        }
    }
}
