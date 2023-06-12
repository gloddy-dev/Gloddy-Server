package com.gloddy.server.acceptance.reliability;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.group_member.event.GroupMemberEstimateCompleteEvent;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import com.gloddy.server.mate.application.MateSaveService;
import com.gloddy.server.praise.application.PraiseService;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.application.GroupMemberUpdateService;
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

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.*;


@RecordApplicationEvents
public class UpdateReliabilityByEstimateTest extends ReliabilityApiTest {

    @MockBean
    private PraiseService praiseService;

    @MockBean
    private MateSaveService mateSaveService;

    @MockBean
    private GroupMemberUpdateService groupMemberUpdateService;

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
        createGroupMember(estimateUser, group);
        createGroupMember(receivePraiseUser, group);
        Estimate request = createEstimateRequest(receivePraiseUser, PraiseValue.KIND);

        // when
        String url = "/api/v1/groups/" + group.getId() + "/group_members" + "/estimate";
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN", accessToken)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        long eventCount = events.stream(GroupMemberEstimateCompleteEvent.class).count();
        Assertions.assertThat(eventCount).isEqualTo(1);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {
        Reliability reliability = reliabilityQueryHandler.findByUserId(user.getId());

        Assertions.assertThat(reliability.getScore()).isEqualTo(ScorePlusType.Estimated.getScore());
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        groupMemberJpaRepository.deleteAll();
        reliabilityRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }
}
