package com.gloddy.server.acceptance.reliability;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.core.event.group_member.GroupMemberReceivePraiseEvent;
import com.gloddy.server.core.event.group_member.GroupMemberSelectBestMateEvent;
import com.gloddy.server.core.event.praise.PraiseCountUpdateEvent;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.group_member.domain.dto.GroupMemberRequest;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import com.gloddy.server.mate.application.MateSaveService;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.application.GroupMemberUpdateService;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.vo.ReliabilityLevel;
import com.gloddy.server.reliability.domain.vo.ScoreMinusType;
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
import org.springframework.transaction.annotation.Transactional;


import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RecordApplicationEvents
public class UpdateReliabilityByAbsenceGroupTest extends ReliabilityApiTest {

    private final static Long INIT_SCORE = 20L;

    @MockBean
    private MateSaveService mateSaveService;

    @MockBean
    private GroupMemberUpdateService groupMemberUpdateService;
    @Autowired
    private ApplicationEvents events;

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
        createPraise(receivePraiseUser);
        Reliability reliability = createReliability(receivePraiseUser);
        reliability.updateScore(INIT_SCORE);
        Group group = createGroup();
        GroupMember loginGroupMember = createGroupMember(loginUser, group);
        GroupMember receivePraiseGroupMember = createGroupMember(receivePraiseUser, group);
        receivePraiseGroupMember.plusAbsenceVoteCount();
        Estimate request = createEstimateRequest(receivePraiseUser, PraiseValue.ABSENCE);


        // then
        String url = "/api/v1/groups/" + group.getId() + "/group_members" + "/estimate";
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN", accessToken)
                .content(objectMapper.writeValueAsString(request))
        );

        // when
        long groupMemberReceivePraiseEventCount = events.stream(GroupMemberReceivePraiseEvent.class).count();
        long reliabilityScoreUpdateEventCount = events.stream(ReliabilityScoreUpdateEvent.class).count();
        Assertions.assertThat(groupMemberReceivePraiseEventCount).isEqualTo(1);
        // 평가 참여로 인한 신뢰도 업데이트 이벤트
        Assertions.assertThat(reliabilityScoreUpdateEventCount).isEqualTo(1);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {
        User receivePraiseUser = userJpaRepository.findFirstByOrderByIdDesc();
        Reliability reliability = reliabilityQueryHandler.findByUserId(receivePraiseUser.getId());

        long praiseCountUpdateEventCount = events.stream(PraiseCountUpdateEvent.class).count();
        // 칭찬 데이터 덥데이트로 인한 이벤트
        Assertions.assertThat(praiseCountUpdateEventCount).isEqualTo(1);

        Assertions.assertThat(reliability.getScore()).isEqualTo(INIT_SCORE - ScoreMinusType.Absence_Group.getScore());
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        groupMemberJpaRepository.deleteAll();
        reliabilityRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }
}
