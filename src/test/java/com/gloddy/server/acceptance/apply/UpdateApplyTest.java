package com.gloddy.server.acceptance.apply;

import com.gloddy.server.apply.entity.Apply;
import com.gloddy.server.apply.entity.vo.Status;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.common.apply.ApplyApiTest;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.utils.event.GroupParticipateEvent;
import com.gloddy.server.estimate.entity.UserGroupAbsence;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UpdateApplyTest extends ApplyApiTest {

    @Autowired
    private ApplicationEvents events;

    @Test
    @DisplayName("sucess_updateApply_거절")
    void successUpdateApplyRefused() throws Exception {
        //given
        //로그인된 사용자이다.
        //자기가 생성한 그룹이 있다. (방장이다.)
        //그 그룹에 지원한 지원서가 존재한다.
        Group myGroup = createMyGroup();
        User applyUser = createUser();
        Apply apply = createApply(applyUser, myGroup);


        //then
        //지원서를 거절한다.
        String url = "/api/v1/groups/" + myGroup.getId() + "/applys/" + apply.getId() + "?status=REFUSE";
        ResultActions test = mockMvc.perform(
                patch(url)
                .header("X-AUTH-TOKEN", accessToken)
        );

        em.flush();
        em.clear();

        //when
        //지원서 상태가 REFUSE된 상태로 변경된다.
        //UserGroup과 AbsenceInGroup이 생성되지 않는다.
        Apply testApply = applyJpaRepository.findFirstByOrderByIdDesc();
        long eventCount = events.stream(GroupParticipateEvent.class).count();
        test.andExpect(status().isNoContent());
        assertThat(testApply.getId()).isEqualTo(apply.getId());
        assertThat(testApply.getUser().getId()).isEqualTo(applyUser.getId());
        assertThat(testApply.getGroup().getId()).isEqualTo(myGroup.getId());
        assertThat(testApply.getStatus()).isEqualTo(Status.REFUSE);
        assertThat(eventCount).isEqualTo(0);
    }

    @Nested
    class successUpdateApplyApproved {
        @Test
        @DisplayName("sucess_updateApply_승인")
        @Transactional
        @Commit
        void beforeEvent() throws Exception {
            //given
            //로그인된 사용자이다.
            //자기가 생성한 그룹이 있다. (방장이다.)
            //그 그룹에 지원한 지원서가 존재한다.
            Group myGroup = createMyGroup();
            User applyUser = createUser();
            Apply apply = createApply(applyUser, myGroup);

            //then
            //지원서를 승인한다.
            String url = "/api/v1/groups/" + myGroup.getId() + "/applys/" + apply.getId() + "?status=APPROVE";
            ResultActions test = mockMvc.perform(
                    patch(url)
                    .header("X-AUTH-TOKEN", accessToken)
            );

            em.flush();
            em.clear();

            //when
            //지원서 상태가 APPROVE된 상태로 변경된다.
            //UserGroup과 AbsenceInGroup이 생성된다.
            test.andExpect(status().isNoContent());
            Apply testApply = applyJpaRepository.findFirstByOrderByIdDesc();
            long eventCount = events.stream(GroupParticipateEvent.class).count();
            assertThat(testApply.getId()).isEqualTo(apply.getId());
            assertThat(testApply.getUser().getId()).isEqualTo(applyUser.getId());
            assertThat(testApply.getGroup().getId()).isEqualTo(myGroup.getId());
            assertThat(testApply.getStatus()).isEqualTo(Status.APPROVE);
            assertThat(eventCount).isEqualTo(1);
        }

        @AfterTransaction
        @Transactional
        @Commit
        void afterEvent() {
            User user = userRepository.findFirstByOrderByIdDesc();
            Group group = groupJpaRepository.findFirstByOrderByIdDesc();
            UserGroup userGroup = userGroupJpaRepository.findFirstByOrderByIdDesc();
            UserGroupAbsence userGroupAbsence = userGroupAbsenceJpaRepository.findFirstByOrderByIdDesc();

            assertThat(userGroup.getGroup().getId()).isEqualTo(group.getId());
            assertThat(userGroup.getUser().getId()).isEqualTo(user.getId());
            assertThat(userGroup.isEnd()).isEqualTo(false);
            assertThat(userGroup.isPraised()).isEqualTo(false);

            assertThat(userGroupAbsence.getUser().getId()).isEqualTo(user.getId());
            assertThat(userGroupAbsence.getGroup().getId()).isEqualTo(group.getId());
            assertThat(userGroupAbsence.getAbsenceCount()).isEqualTo(0);
            assertThat(userGroupAbsence.getAbsence()).isEqualTo(false);


            reliabilityRepository.deleteAll();
            userGroupAbsenceJpaRepository.deleteAll();
            userGroupJpaRepository.deleteAll();
            applyJpaRepository.deleteAll();
            groupJpaRepository.deleteAll();
            praiseJpaRepository.deleteAll();
            userRepository.deleteAll();
        }
    }

    @Test
    @DisplayName("fail_updateApply - 방장이 아닐 때")
    void failUpdateApplyByNotCaptain() throws Exception {
        //given
        //로그인 된 사용자이다.
        User user1 = user; // 방장이 아닌 사용자 - 요청을 보낼 사람
        User user2 = createUser(); // 그룹의 방장인 사용자
        User user3 = createUser(); // 그룹에 지원한 사용자

        Group group = createGroup(user2);
        Apply apply = createApply(user3, group);

        //when
        //자신이 방장이 아닌 그룹에서 지원서 승인 or 거절 요청을 한다.
        String url = "/api/v1/groups/" + group.getId() + "/applys/" + apply.getId() + "?status=APPROVE";
        ResultActions test = mockMvc.perform(
                patch(url)
                .header("X-AUTH-TOKEN", accessToken)
        );

        //then
        //알맞는 예외 메시지를 받는다.
        test.andExpect(status().isForbidden());
        test.andExpect(jsonPath("status").value(ErrorCode.GROUP_NOT_CAPTAIN.getStatus()));
        test.andExpect(jsonPath("message").value(ErrorCode.GROUP_NOT_CAPTAIN.name()));
        test.andExpect(jsonPath("reason").value(ErrorCode.GROUP_NOT_CAPTAIN.getErrorMessage()));
    }
}
