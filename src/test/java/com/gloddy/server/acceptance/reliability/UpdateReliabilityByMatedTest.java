package com.gloddy.server.acceptance.reliability;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.common.reliability.ReliabilityApiTest;
import com.gloddy.server.group_member.event.GroupMemberEstimateCompleteEvent;
import com.gloddy.server.mate.event.MateCreateEvent;
import com.gloddy.server.user.domain.vo.PraiseValue;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import com.gloddy.server.user.domain.vo.ScorePlusType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RecordApplicationEvents
public class UpdateReliabilityByMatedTest extends ReliabilityApiTest {

    @Autowired
    private ApplicationEvents events;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Test
    @DisplayName("최고의 짝꿍으로 선정된 유저 신뢰도 점수 업데이트 테스트")
    @Transactional
    @Commit
    void successUpdateReliabilityByMatedTest() throws Exception {
        // given
        User estimateUser = user;
        User receiveMateUser = createUser();
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

        verifyUserReliability();

        groupMemberJpaRepository.deleteAll();
        mateJpaRepository.deleteAll();
        groupJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

    private void verifyUserReliability() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        transactionTemplate.execute(status -> {
            User receivePraiseUser = userJpaRepository.findFirstByOrderByIdDesc();
            Assertions.assertThat(receivePraiseUser.getReliability().getScore())
                    .isEqualTo(ScorePlusType.Praised.getScore() + ScorePlusType.Mated.getScore());
            Assertions.assertThat(receivePraiseUser.getReliability().getLevel()).isEqualTo(ReliabilityLevel.HOOD);
            return null;
        });
    }
}
