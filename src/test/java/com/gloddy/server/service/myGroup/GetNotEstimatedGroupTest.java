package com.gloddy.server.service.myGroup;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.common.myGroup.GroupServiceTest;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group_member.domain.dto.GroupMemberRequest;
import com.gloddy.server.myGroup.read.MyGroupReadService;
import com.gloddy.server.myGroup.read.dto.MyGroupResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

public class GetNotEstimatedGroupTest extends GroupServiceTest {

    @Autowired
    private MyGroupReadService myGroupReadService;

    @Nested
    class Case1 {

        private Long targetUserId;
        private Long groupId;

        @Test
        @Commit
        void getNotEstimatedGroups_returns_all() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand = createGroupCreateCommand(
                    LocalDate.now().minusDays(1),
                    "12:00");
            groupId = createGroup(captainId, groupCreateCommand);

            Long applierId = createUser();
            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            Long applyId = createApply(applierId, groupId, applyCreateCommand);

            approveApply(captainId, applyId);

            targetUserId = applierId;
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            MyGroupResponse.NotEstimated notEstimatedGroups = myGroupReadService.getNotEstimatedGroups(targetUserId);


            //then
            assertThat(notEstimatedGroups.getGroups()).hasSize(1);
            assertThat(notEstimatedGroups.getGroups().get(0).getIsCaptain()).isFalse();
            assertThat(notEstimatedGroups.getGroups().get(0).getGroup().getGroupId()).isEqualTo(groupId);

            //after
            clear();
        }
    }

    @Nested
    class Case2 {

        private Long captainId;
        private Long groupId;
        private Long targetUserId;

        @BeforeEach
        void given() {
            //given...
            captainId = createUser();

            GroupRequest.Create groupCreateCommand = createGroupCreateCommand(
                    LocalDate.now().plusDays(1),
                    "12:00");
            groupId = createGroup(captainId, groupCreateCommand);

            mockLocalDateTimeToTwoDayAfterGroupStartDateTime();

            Long applierId = createUser();
            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            Long applyId = createApply(applierId, groupId, applyCreateCommand);

            approveApply(captainId, applyId);

            targetUserId = applierId;

            TestTransaction.end();
        }

        @Test
        @Transactional
        @Commit
        void getNotEstimatedGroups_returns_empty_list_when_all_end_estimate() {
            //after_event_given
            GroupMemberRequest.Estimate estimateCommand = createEstimateCommand(List.of(captainId), captainId);
            groupMemberService.estimateGroupMembers(estimateCommand, targetUserId, groupId);

        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            MyGroupResponse.NotEstimated notEstimatedGroups = myGroupReadService.getNotEstimatedGroups(targetUserId);

            //then
            assertThat(notEstimatedGroups.getGroups()).isEmpty();

            //after
            clear();
        }
    }
}
