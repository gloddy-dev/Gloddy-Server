package com.gloddy.server.acceptance.reliability;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.group_member.event.GroupMemberEstimateCompleteEvent;
import com.gloddy.server.group_member.event.GroupMemberReceivePraiseEvent;
import com.gloddy.server.user.domain.vo.PraiseValue;
import com.gloddy.server.mate.application.MateSaveService;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import com.gloddy.server.user.domain.vo.ScoreMinusType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RecordApplicationEvents
public class UpdateReliabilityByAbsenceGroupTest extends ReliabilityApiTest {

    private final static Long INIT_SCORE = 20L;

    @MockBean
    private MateSaveService mateSaveService;

    @Autowired
    private ApplicationEvents events;

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 신뢰도 20, HOOD -> 모임 불참 평가 -> 신뢰도 10, HOOD
     */
    @Test
    @DisplayName("모임 불참 신뢰도 점수 업데이트 테스트")
    @Transactional
    @Commit
    void successUpdateReliabilityByAbsenceGroupTest() throws Exception {
        // given
        // 모임 불참 투표 과반수 이상
        User loginUser = user;
        User receivePraiseUser = createUser();

        User findReceivePraiseUser = userJpaRepository.findById(receivePraiseUser.getId()).orElseThrow();
        findReceivePraiseUser.getReliability().updateScore(INIT_SCORE);
        Group group = createGroup();
        GroupMember loginGroupMember = createGroupMember(loginUser, group);
        GroupMember receivePraiseGroupMember = createGroupMember(receivePraiseUser, group);
        receivePraiseGroupMember.plusAbsenceVoteCount();
        Estimate request = createEstimateRequest(receivePraiseUser, PraiseValue.ABSENCE);

        em.flush();
        em.clear();

        // then
        String url = "/api/v1/groups/" + group.getId() + "/group_members" + "/estimate";
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN", accessToken)
                .content(objectMapper.writeValueAsString(request))
        );

        // when
        long groupMemberReceivePraiseEventCount = events.stream(GroupMemberReceivePraiseEvent.class).count();
        long groupMemberEstimateCompleteEventCount = events.stream(GroupMemberEstimateCompleteEvent.class).count();
        Assertions.assertThat(groupMemberReceivePraiseEventCount).isEqualTo(1);
        Assertions.assertThat(groupMemberEstimateCompleteEventCount).isEqualTo(1);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {

        verifyUserReliability();

        groupMemberJpaRepository.deleteAll();
        groupJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

    private void verifyUserReliability() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(status -> {
            User receivePraiseUser = userJpaRepository.findFirstByOrderByIdDesc();
            Assertions.assertThat(receivePraiseUser.getReliability().getScore())
                    .isEqualTo(INIT_SCORE - ScoreMinusType.Absence_Group.getScore());
            Assertions.assertThat(receivePraiseUser.getReliability().getLevel()).isEqualTo(ReliabilityLevel.HOOD);
            return null;
        });
    }
}
