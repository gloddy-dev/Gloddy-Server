package com.gloddy.server.reliability;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.domain.AbsenceInGroupDomain;
import com.gloddy.server.estimate.dto.EstimateRequest;
import com.gloddy.server.estimate.entity.AbsenceInGroup;
import com.gloddy.server.estimate.entity.embedded.PraiseValue;
import com.gloddy.server.estimate.service.mate.MateSaveService;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.service.UserGroupUpdateService;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import com.gloddy.server.reliability.entity.vo.ScoreMinusType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@RecordApplicationEvents
public class UpdateReliabilityByAbsenceGroupTest extends ReliabilityApiTest {

    private final static Long INIT_SCORE = 20L;

    @Mock
    private AbsenceInGroupDomain absenceInGroupDomain;

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
        updateReliabilityScore(INIT_SCORE);
        Group group = createGroup();
        AbsenceInGroup absenceInGroup = createAbsenceInGroup(user, group);
        EstimateRequest request = createEstimateRequest(PraiseValue.ABSENCE);

        BDDMockito.willReturn(true)
                .given(absenceInGroupDomain).checkAbsenceCountOver();

        // then
        String url = "/api/v1/groups/" + group.getId() + "/estimate";
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN", accessToken)
                .content(objectMapper.writeValueAsString(request))
        );

        // when
        long eventCount = events.stream(ReliabilityScoreUpdateEvent.class).count();
        Assertions.assertThat(eventCount).isEqualTo(1);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {
        Reliability reliability = reliabilityQueryHandler.findByUser(user);

        Assertions.assertThat(reliability.getScore()).isEqualTo(INIT_SCORE - ScoreMinusType.Absence_Group.getScore());
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        absenceInGroupJpaRepository.deleteAll();
        userGroupJpaRepository.deleteAll();
        reliabilityRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        userRepository.deleteAll();
    }
}
