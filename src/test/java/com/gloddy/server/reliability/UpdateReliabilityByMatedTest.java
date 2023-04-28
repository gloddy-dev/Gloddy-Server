package com.gloddy.server.reliability;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.estimate.dto.EstimateRequest;
import com.gloddy.server.estimate.entity.embedded.PraiseValue;
import com.gloddy.server.estimate.service.praise.PraiseService;
import com.gloddy.server.group.entity.Group;
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
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RecordApplicationEvents
public class UpdateReliabilityByMatedTest extends ReliabilityApiTest {

    @MockBean
    private PraiseService praiseService;

    @MockBean
    private UserGroupUpdateService userGroupUpdateService;

    @Autowired
    private ApplicationEvents events;

    @Test
    @DisplayName("최고의 짝꿍으로 선정된 유저 신뢰도 점수 업데이트 테스트")
    @Transactional
    @Commit
    void successUpdateReliabilityByMatedTest() throws Exception {
        // given
        User estimateUser = user;
        User receiveMateUser = createUser();
        createReliability(receiveMateUser);
        EstimateRequest request = createEstimateRequest(receiveMateUser, PraiseValue.KIND);
        Group group = createGroup();

        // when
        String url = "/api/v1/groups/" + group.getId() + "/estimate";
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN", accessToken)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        result.andExpect(status().isNoContent());
        long eventCount = events.stream(ReliabilityScoreUpdateEvent.class).count();
        Assertions.assertThat(eventCount).isEqualTo(2);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {
        User receiveMateUser = userRepository.findFirstByOrderByIdDesc();
        Reliability reliability = reliabilityQueryHandler.findByUserId(receiveMateUser.getId());

        Assertions.assertThat(reliability.getScore()).isEqualTo(ScorePlusType.Mated.getScore());
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        userGroupJpaRepository.deleteAll();
        mateJpaRepository.deleteAll();
        reliabilityRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        userRepository.deleteAll();
    }
}
