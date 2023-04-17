package com.gloddy.server.reliability;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.estimate.dto.EstimateRequest;
import com.gloddy.server.estimate.entity.AbsenceInGroup;
import com.gloddy.server.estimate.entity.Praise;
import com.gloddy.server.estimate.service.AbsenceInGroupFindService;
import com.gloddy.server.estimate.service.mate.MateSaveService;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.service.UserGroupUpdateService;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import com.gloddy.server.reliability.entity.vo.ScorePlusType;
import com.gloddy.server.user.service.UserFindService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.BDDMockito.*;

@RecordApplicationEvents
public class UpdateReliabilityByPraisedTest extends ReliabilityApiTest {

    @MockBean
    private MateSaveService mateSaveService;

    @MockBean
    private UserGroupUpdateService userGroupUpdateService;

    @MockBean
    private AbsenceInGroupFindService absenceInGroupFindService;

    @Autowired
    private ApplicationEvents events;

    @Test
    @Transactional
    @Commit
    @DisplayName("칭찬 받은 유저 신뢰도 점수 업데이트 테스트")
    void successUpdateReliabilityByPraisedTest() throws Exception {
        // given
        Group group = createGroup();
        EstimateRequest request = createEstimateRequest();

        given(absenceInGroupFindService.findByGroupIdAndUserId(group.getId(), user.getId()))
                .willReturn(Mockito.mock(AbsenceInGroup.class));

        // when
        Praise praise = praiseJpaRepository.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("parise 없음"));
        System.out.println(praise.getId());

        String url = "/api/v1/groups/" + group.getId() + "/estimate";
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN", accessToken)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        long eventCount = events.stream(ReliabilityScoreUpdateEvent.class).count();
        Assertions.assertThat(eventCount).isEqualTo(1);
    }

    @AfterTransaction
    void afterEvent() {
        Reliability reliability = reliabilityQueryHandler.findByUser(user);

        Assertions.assertThat(reliability.getScore()).isEqualTo(ScorePlusType.Praised.getScore());
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);
    }
}
