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

public class GetHostingGroupTest extends MyGroupServiceTest {

    @Autowired
    private MyGroupReadService myGroupReadService;

    @Nested
    class Case1 {

        private Long targetUserId;
        private Long groupId1;
        private Long groupId2;

        @Test
        @Commit
        void getHostingGroups_returns_all() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand1 = createGroupCreateCommand(LocalDate.now().plusDays(1), "12:00", "13:00");
            GroupRequest.Create groupCreateCommand2 = createGroupCreateCommand(LocalDate.now().plusDays(2), "12:00", "13:00");
            groupId1 = createGroup(captainId, groupCreateCommand1);
            groupId2 = createGroup(captainId, groupCreateCommand2);

            targetUserId = captainId;
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            MyGroupResponse.Hosting hostingGroups = myGroupReadService.getHostingGroups(targetUserId);

            //then
            assertThat(hostingGroups.getGroups()).hasSize(2);
            assertThat(hostingGroups.getGroups().get(0).getIsExistNewApply()).isFalse();
            assertThat(hostingGroups.getGroups().get(1).getIsExistNewApply()).isFalse();
            assertThat(hostingGroups.getGroups().get(0).getGroup().getGroupId()).isEqualTo(groupId2);
            assertThat(hostingGroups.getGroups().get(1).getGroup().getGroupId()).isEqualTo(groupId1);

            //after
            clear();
        }
    }

    @Nested
    class Case2 {

        Long targetUserId;

        @Test
        @Commit
        void getHostingGroups_returns_empty_list_when_no_hostingGroups() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand1 = createGroupCreateCommand(LocalDate.now().plusDays(1), "12:00", "13:00");
            Long groupId1 = createGroup(captainId, groupCreateCommand1);

            targetUserId = createUser();
            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            Long applyId1 = createApply(targetUserId, groupId1, applyCreateCommand);

            approveApply(captainId, applyId1);
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            MyGroupResponse.Hosting hostingGroups = myGroupReadService.getHostingGroups(targetUserId);

            //then
            assertThat(hostingGroups.getGroups()).isEmpty();

            //after
            clear();
        }
    }

    @Nested
    class Case3 {

        Long targetUserId;
        Long groupId;

        @Test
        @Commit
        void getHostingGroups_returns_all_with_new_apply() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand1 = createGroupCreateCommand(LocalDate.now().plusDays(1), "12:00", "13:00");
            groupId = createGroup(captainId, groupCreateCommand1);

            Long applierId = createUser();
            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            createApply(applierId, groupId, applyCreateCommand);

            targetUserId = captainId;
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            MyGroupResponse.Hosting hostingGroups = myGroupReadService.getHostingGroups(targetUserId);

            //then
            assertThat(hostingGroups.getGroups()).hasSize(1);
            assertThat(hostingGroups.getGroups().get(0).getIsExistNewApply()).isTrue();
            assertThat(hostingGroups.getGroups().get(0).getGroup().getGroupId()).isEqualTo(groupId);

            clear();
        }
    }
}
