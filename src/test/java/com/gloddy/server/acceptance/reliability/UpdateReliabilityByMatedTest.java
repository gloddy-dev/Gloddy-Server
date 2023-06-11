package com.gloddy.server.acceptance.reliability;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.group_member.event.GroupMemberEstimateCompleteEvent;
import com.gloddy.server.mate.event.MateCreateEvent;
import com.gloddy.server.praise.domain.vo.PraiseValue;
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
import org.springframework.transaction.annotation.Transactional;


import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RecordApplicationEvents
public class UpdateReliabilityByMatedTest extends ReliabilityApiTest {

    @MockBean
    private PraiseService praiseService;

    @MockBean
    private GroupMemberUpdateService groupMemberUpdateService;

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
        Estimate request = createEstimateRequest(receiveMateUser, PraiseValue.KIND);
        Group group = createGroup();
        createGroupMember(estimateUser, group);
        createGroupMember(receiveMateUser, group);

        // when
        String url = "/api/v1/groups/" + group.getId() + "/group_members" + "/estimate";
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-AUTH-TOKEN", accessToken)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        result.andExpect(status().isNoContent());
        long mateCreateEventCount = events.stream(MateCreateEvent.class).count();
        long groupMemberEstimateCompleteEvent = events.stream(GroupMemberEstimateCompleteEvent.class).count();
        Assertions.assertThat(mateCreateEventCount).isEqualTo(1);
        Assertions.assertThat(groupMemberEstimateCompleteEvent).isEqualTo(1);
    }

    @AfterTransaction
    @Transactional
    @Commit
    void afterEvent() {
        User receiveMateUser = userJpaRepository.findFirstByOrderByIdDesc();
        Reliability reliability = reliabilityQueryHandler.findByUserId(receiveMateUser.getId());

        Assertions.assertThat(reliability.getScore()).isEqualTo(ScorePlusType.Mated.getScore());
        Assertions.assertThat(reliability.getLevel()).isEqualTo(ReliabilityLevel.HOOD);

        groupMemberJpaRepository.deleteAll();
        mateJpaRepository.deleteAll();
        reliabilityRepository.deleteAll();
        groupJpaRepository.deleteAll();
        praiseJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }
}
