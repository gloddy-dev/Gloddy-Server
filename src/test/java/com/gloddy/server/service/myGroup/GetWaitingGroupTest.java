package com.gloddy.server.service.myGroup;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.common.myGroup.MyGroupServiceTest;
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

public class GetWaitingGroupTest extends MyGroupServiceTest {

    @Autowired
    private MyGroupReadService myGroupReadService;

    @Nested
    class Case1 {

        private Long targetUserId;
        private Long groupId1;
        private Long groupId2;

        @Test
        @Commit
        void getWaitingGroups_returns_all() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand1 = createGroupCreateCommand(
                    LocalDate.now().plusDays(1),
                    "12:00",
                    "13:00");
            GroupRequest.Create groupCreateCommand2 = createGroupCreateCommand(
                    LocalDate.now().plusDays(1),
                    "12:00",
                    "13:00");
            groupId1 = createGroup(captainId, groupCreateCommand1);
            groupId2 = createGroup(captainId, groupCreateCommand2);

            Long applierId = createUser();
            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            createApply(applierId, groupId1, applyCreateCommand);
            createApply(applierId, groupId2, applyCreateCommand);

            targetUserId = applierId;
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            MyGroupResponse.Waiting waitingGroups = myGroupReadService.getWaitingGroups(targetUserId);

            //then
            assertThat(waitingGroups.getGroups()).hasSize(2);
            assertThat(waitingGroups.getGroups().get(0).getGroup().getGroupId()).isEqualTo(groupId2);
            assertThat(waitingGroups.getGroups().get(1).getGroup().getGroupId()).isEqualTo(groupId1);

            //after
            clear();
        }
    }

}
