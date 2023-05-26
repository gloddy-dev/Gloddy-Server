package com.gloddy.server.acceptance.reliability;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.core.event.reliability.ReliabilityScoreUpdateEvent;
import com.gloddy.server.estimate.domain.dto.EstimateRequest;
import com.gloddy.server.estimate.domain.vo.PraiseValue;
import com.gloddy.server.estimate.application.mate.MateSaveService;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user_group.application.UserGroupUpdateService;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.vo.ReliabilityLevel;
import com.gloddy.server.reliability.domain.vo.ScorePlusType;
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
public class UpdateReliabilityByPraisedTest extends ReliabilityApiTest {

    @MockBean
    private MateSaveService mateSaveService;

    @MockBean
    private UserGroupUpdateService userGroupUpdateService;

    @Autowired
    private ApplicationEvents events;

    @Test
    @Transactional
    @Commit
    @DisplayName("칭찬 받은 유저 신뢰도 점수 업데이트 테스트")
    void successUpdateReliabilityByPraisedTest() throws Exception {
        // given
        User loginUser = user;
        User receivePraiseUser = createUser();
        createPraise(receivePraiseUser);
        createReliability(receivePraiseUser);
        Group group = createGroup();
        createUserGroup(receivePraiseUser, group);
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
        Assertions.assertThat(eventCount).isEqualTo(2);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {
        User receivePraiseUser = userJpaRepository.findFirstByOrderByIdDesc();
        Reliability reliability = reliabilityQueryHandler.findByUserId(receivePraiseUser.getId());

        Assertions.assertThat(reliability.getScore()).isEqualTo(ScorePlusType.Praised.getScore());
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        userGroupJpaRepository.deleteAll();
        reliabilityRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }
}
