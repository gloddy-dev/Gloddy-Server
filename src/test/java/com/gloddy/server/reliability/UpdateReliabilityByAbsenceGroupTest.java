package com.gloddy.server.reliability;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.estimate.dto.EstimateRequest;
import com.gloddy.server.estimate.entity.embedded.PraiseValue;
import com.gloddy.server.estimate.service.mate.MateSaveService;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.service.UserGroupUpdateService;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import com.gloddy.server.reliability.entity.vo.ScoreMinusType;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RecordApplicationEvents
public class UpdateReliabilityByAbsenceGroupTest extends ReliabilityApiTest {

    private final static Long INIT_SCORE = 20L;

    @MockBean
    private MateSaveService mateSaveService;

    @MockBean
    private UserGroupUpdateService userGroupUpdateService;
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
        UserGroup loginUserGroup = createUserGroup(loginUser, group);
        UserGroup receivePraiseUserGroup = createUserGroup(receivePraiseUser, group);
        receivePraiseUserGroup.plusAbsenceVoteCount();
        EstimateRequest request = createEstimateRequest(receivePraiseUser, PraiseValue.ABSENCE);


        // then
        String url = "/api/v1/groups/" + group.getId() + "/estimate";
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN", accessToken)
                .content(objectMapper.writeValueAsString(request))
        );

        // when
        long eventCount = events.stream(ReliabilityScoreUpdateEvent.class).count();
        // 불참 칭찬 받음 + 평가참여 -> 이벤트 2번
        Assertions.assertThat(eventCount).isEqualTo(2);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {
        User receivePraiseUser = userRepository.findFirstByOrderByIdDesc();
        Reliability reliability = reliabilityQueryHandler.findByUserId(receivePraiseUser.getId());

        Assertions.assertThat(reliability.getScore()).isEqualTo(INIT_SCORE - ScoreMinusType.Absence_Group.getScore());
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        userGroupJpaRepository.deleteAll();
        reliabilityRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        userRepository.deleteAll();
    }
}
