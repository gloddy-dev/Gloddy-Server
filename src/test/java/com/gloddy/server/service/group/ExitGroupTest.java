package com.gloddy.server.service.group;

import com.gloddy.server.apply.domain.dto.ApplyRequest;
import com.gloddy.server.common.myGroup.GroupServiceTest;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group_member.application.GroupMemberDeleteService;
import com.gloddy.server.group_member.domain.GroupMember;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ExitGroupTest extends GroupServiceTest {

    @Autowired
    private GroupMemberDeleteService groupMemberDeleteService;

    @Nested
    class Case1 {

        private Long captainId;
        private Long deletedUserId;
        private Long applierId2;
        private Long groupId;

        @Test
        @Commit
        void exit_group_success() {

            //캡틴이 그룹을 생성합니다.
            captainId = createUser();
            GroupRequest.Create groupCreateCommand = createGroupCreateCommand(
                    LocalDate.now().plusDays(1),
                    "12:00"
            );
            groupId = createGroup(captainId, groupCreateCommand);

            //두명의 지원자들을 승낙해 그룹 멤버가 됩니다. (총 3명)
            Long applierId1 = createUser();
            applierId2 = createUser();

            ApplyRequest.Create applyCreateCommand = createApplyCreateCommand();
            Long applyId1 = createApply(applierId1, groupId, applyCreateCommand);
            Long applyId2 = createApply(applierId2, groupId, applyCreateCommand);

            approveApply(captainId, applyId1);
            approveApply(captainId, applyId2);

            deletedUserId = applierId1;
        }

        @AfterTransaction
        @Commit
        void when_and_then() {

            transactionTemplate.execute(status -> {
                //when
                //applierId1의 그룹 멤버가 그룹에서 나갑니다.
                groupMemberDeleteService.delete(deletedUserId, groupId);

                //then
                Group group = groupJpaRepository.findById(groupId).orElseThrow();
                List<GroupMember> groupMembers = groupMemberJpaRepository.findAll();

                //그룹 멤버는 2명이됩니다.
                assertThat(group.getMemberCount()).isEqualTo(2);
                assertThat(groupMembers.size()).isEqualTo(2);
                return null;
            });

            //after
            clear();
        }
    }
}
