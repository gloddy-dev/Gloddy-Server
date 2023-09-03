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

public class GetParticipatingGroupTest extends GroupServiceTest {

    @Autowired
    private MyGroupReadService myGroupReadService;

    @Nested
    class getParticipatingGroups_returns_all {
        Long targetUserId;
        Long groupId1;
        Long groupId2;

        @Test
        @Commit
        void start() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand1 = createGroupCreateCommand(LocalDate.now().plusDays(1), "12:00");
            GroupRequest.Create groupCreateCommand2 = createGroupCreateCommand(LocalDate.now().plusDays(2), "12:00");
            groupId1 = createGroup(captainId, groupCreateCommand1);
            groupId2 = createGroup(captainId, groupCreateCommand2);

            targetUserId = createUser();
            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            Long applyId1 = createApply(targetUserId, groupId1, applyCreateCommand);
            Long applyId2 = createApply(targetUserId, groupId2, applyCreateCommand);

            approveApply(captainId, applyId1);
            approveApply(captainId, applyId2);
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            MyGroupResponse.Participating participatingGroups = myGroupReadService.getParticipatingGroups(targetUserId);

            //then
            assertThat(participatingGroups.getGroups()).hasSize(2);
            assertThat(participatingGroups.getGroups().get(0).getIsNew()).isTrue();
            assertThat(participatingGroups.getGroups().get(0).getGroup().getGroupId()).isEqualTo(groupId2);
            assertThat(participatingGroups.getGroups().get(1).getIsNew()).isTrue();
            assertThat(participatingGroups.getGroups().get(1).getGroup().getGroupId()).isEqualTo(groupId1);

            //after
            clear();
        }
    }

    @Nested
    class getParticipatingGroups_returns_empty_list_when_all_groups_captain {

        private Long targetUserId;

        @Test
        @Commit
        void start() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand1 = createGroupCreateCommand(LocalDate.now().plusDays(1), "12:00");
            createGroup(captainId, groupCreateCommand1);

            targetUserId = captainId;
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {

            //when
            MyGroupResponse.Participating participatingGroups = myGroupReadService.getParticipatingGroups(targetUserId);

            //then
            assertThat(participatingGroups.getGroups()).isEmpty();

            //after
            clear();
        }
    }

    @Nested
    class getParticipatingGroups_returns_empty_list_when_all_groups_end {

        private Long targetUserId;

        @Test
        @Commit
        void start() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand1 = createGroupCreateCommand(LocalDate.now().minusDays(1), "12:00");
            GroupRequest.Create groupCreateCommand2 = createGroupCreateCommand(LocalDate.now().minusDays(2), "12:00");
            Long groupId1 = createGroup(captainId, groupCreateCommand1);
            Long groupId2 = createGroup(captainId, groupCreateCommand2);

            targetUserId = createUser();
            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            Long applyId1 = createApply(targetUserId, groupId1, applyCreateCommand);
            Long applyId2 = createApply(targetUserId, groupId2, applyCreateCommand);

            approveApply(captainId, applyId1);
            approveApply(captainId, applyId2);
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {

            //when
            MyGroupResponse.Participating participatingGroups = myGroupReadService.getParticipatingGroups(targetUserId);

            //then
            assertThat(participatingGroups.getGroups()).isEmpty();

            //after
            clear();
        }
    }
}
