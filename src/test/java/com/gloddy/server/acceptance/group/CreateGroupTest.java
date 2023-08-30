package com.gloddy.server.acceptance.group;

import com.gloddy.server.common.group.GroupApiTest;
import com.gloddy.server.core.event.GroupParticipateEvent;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RecordApplicationEvents
public class CreateGroupTest extends GroupApiTest {

    @Autowired
    private ApplicationEvents events;

    @Test
    @DisplayName("sucess_createGroup")
    @Transactional
    @Commit
    void successCreateGroup() throws Exception {
        //given
        //로그인된 사용자이다.
        // 생성할 모임의 정보를 다 입력한다.
        GroupRequest.Create request = createGroupCreateRequest();

        //when
        //모임을 생성한다.
        ResultActions test = mockMvc.perform(
                post("/api/v1/group-create")
                        .header("X-AUTH-TOKEN", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        //자기가 captain인 그룹이 생성된다.
        //userGroup이 생성된다
        //absenceInGroup이 생성된다.
        Group group = groupJpaRepository.findFirstByOrderByIdDesc();
        long eventCount = events.stream(GroupParticipateEvent.class).count();

        test.andExpect(status().isOk());
        test.andExpect(jsonPath("groupId").value(group.getId()));
        assertThat(eventCount).isEqualTo(1);
        assertThat(group.getCaptain().getId()).isEqualTo(user.getId());
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {
        Group group = groupJpaRepository.findFirstByOrderByIdDesc();
        GroupMember groupMember = groupMemberJpaRepository.findFirstByOrderByIdDesc();

        assertThat(groupMember.getUser().getId()).isEqualTo(user.getId());
        assertThat(groupMember.getGroup().getId()).isEqualTo(group.getId());
        assertThat(groupMember.isEnd()).isEqualTo(false);
        assertThat(groupMember.isEndEstimate()).isEqualTo(false);
        assertThat(groupMember.getAbsenceVoteCount()).isEqualTo(0);
        assertThat(groupMember.isAbsence()).isFalse();

        reliabilityRepository.deleteAll();
        groupMemberJpaRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }
}
