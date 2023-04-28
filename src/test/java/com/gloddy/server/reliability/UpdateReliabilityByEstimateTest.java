package com.gloddy.server.reliability;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.estimate.dto.EstimateRequest;
import com.gloddy.server.estimate.entity.embedded.PraiseValue;
import com.gloddy.server.estimate.service.mate.MateSaveService;
import com.gloddy.server.estimate.service.praise.PraiseService;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.service.UserGroupUpdateService;
import com.gloddy.server.reliability.entity.Reliability;
import com.gloddy.server.reliability.entity.vo.ReliabilityLevel;
import com.gloddy.server.reliability.entity.vo.ScorePlusType;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;


@RecordApplicationEvents
public class UpdateReliabilityByEstimateTest extends ReliabilityApiTest {

    @MockBean
    private PraiseService praiseService;

    @MockBean
    private MateSaveService mateSaveService;

    @MockBean
    private UserGroupUpdateService userGroupUpdateService;

    @Autowired
    private ApplicationEvents events;

    @Test
    @Transactional
    @Commit
    @DisplayName("평가 참여 신뢰도 점수 업데이트 테스트")
    void successUpdateReliabilityByEstimate() throws Exception {
        // given
        User estimateUser = user;
        User receivePraiseUser = createUser();
        Group group = createGroup();
        UserGroup userGroup = createUserGroup(estimateUser, group);
        EstimateRequest request = createEstimateRequest(receivePraiseUser, PraiseValue.KIND);

        // when
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
    @Transactional
    @Commit
    void afterEvent() {
        Reliability reliability = reliabilityQueryHandler.findByUserId(user.getId());

        Assertions.assertThat(reliability.getScore()).isEqualTo(ScorePlusType.Estimated.getScore());
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        userGroupJpaRepository.deleteAll();
        reliabilityRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        userRepository.deleteAll();
    }
}
