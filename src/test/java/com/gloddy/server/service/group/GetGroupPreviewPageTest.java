package com.gloddy.server.service.group;

import com.gloddy.server.common.myGroup.GroupServiceTest;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.application.GroupService;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.dto.GroupResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class GetGroupPreviewPageTest extends GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Nested
    class Case1 {

        private Long groupId;

        @Test
        @Commit
        void getAllGroupPreview_returns_All() {
            //given
            Long captainId = createUser();
            GroupRequest.Create groupCreateCommand =
                    createGroupCreateCommand(LocalDate.now().plusDays(1), "12:00", "13:00");
            groupId = createGroup(captainId, groupCreateCommand);
        }

        @AfterTransaction
        @Transactional
        @Commit
        void when_and_then() {
            //when
            PageResponse<GroupResponse.GetGroup> groupPreviewPageResponse = groupService.getGroups(5, 0);

            //then
            assertThat(groupPreviewPageResponse.getTotalPage()).isEqualTo(1);
            assertThat(groupPreviewPageResponse.getCurrentPage()).isEqualTo(0);
            assertThat(groupPreviewPageResponse.getTotalCount()).isEqualTo(1);
            assertThat(groupPreviewPageResponse.getCurrentCount()).isEqualTo(1);
            assertThat(groupPreviewPageResponse.getContents().get(0).getGroupId()).isEqualTo(groupId);

            //after
            clear();
        }
    }
}
