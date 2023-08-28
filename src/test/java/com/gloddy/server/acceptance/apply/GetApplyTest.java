package com.gloddy.server.acceptance.apply;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.apply.ApplyApiTest;
import com.gloddy.server.group.domain.Group;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.gloddy.server.core.error.handler.errorCode.ErrorCode.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetApplyTest extends ApplyApiTest {

    private ResultActions apiCall(User user, Long groupId) throws Exception {
        String accessToken = getTokenAfterLogin(user);

        return mockMvc.perform(
                get("/api/v1/groups/" + groupId + "/applies")
                        .header("X-AUTH-TOKEN", accessToken)
        );
    }

    @Test
    @DisplayName("지원서 전체 조회 성공")
    void success_get_applies() throws Exception {
        //given
        Group group = createGroup(this.user);

        User firstUser = createUser();
        User secondUser = createUser();

        Apply alreadyUpdateApply = createApply(createUser(), group);
        alreadyUpdateApply.refuseApply();

        Apply firstApply = group.createApply(firstUser, "introduce", "reason");
        Apply secondApply = group.createApply(secondUser, "introduce", "reason");

        applyJpaRepository.saveAll(List.of(firstApply, secondApply));

        em.flush();
        em.clear();

        //when
        ResultActions test = apiCall(user, group.getId());

        User user1 = userJpaRepository.findById(firstUser.getId()).orElseThrow();
        User user2 = userJpaRepository.findById(secondUser.getId()).orElseThrow();

        //then
        test.andExpect(status().isOk());
        test.andExpect(jsonPath("totalCount").value(2));
        test.andExpect(jsonPath("applies.size()").value(2));
        test.andExpect(jsonPath("applies[0].userId").value(user1.getId()));
        test.andExpect(jsonPath("applies[0].userNickname").value(user1.getNickName()));
        test.andExpect(jsonPath("applies[0].userImageUrl").value(user1.getImageUrl()));
        test.andExpect(jsonPath("applies[0].reliabilityLevel").value(user1.getReliabilityLevel().toString()));
        test.andExpect(jsonPath("applies[0].introduce").value(firstApply.getContent()));
        test.andExpect(jsonPath("applies[0].reason").value(firstApply.getReason()));
        test.andExpect(jsonPath("applies[1].userId").value(user2.getId()));
        test.andExpect(jsonPath("applies[1].userNickname").value(user2.getNickName()));
        test.andExpect(jsonPath("applies[1].userImageUrl").value(user2.getImageUrl()));
        test.andExpect(jsonPath("applies[1].reliabilityLevel").value(user2.getReliabilityLevel().toString()));
        test.andExpect(jsonPath("applies[1].introduce").value(secondApply.getContent()));
        test.andExpect(jsonPath("applies[1].reason").value(secondApply.getReason()));
    }

    @Test
    @DisplayName("지원서 전체 조회 실패 - 방장이 아님")
    void fail_get_applies_by_no_captain() throws Exception {
        Group group = createGroup(createUser());

        ResultActions test = apiCall(user, group.getId());

        test.andExpect(status().isForbidden());
        test.andExpect(jsonPath("status").value(GROUP_NOT_CAPTAIN.getStatus()));
        test.andExpect(jsonPath("message").value(GROUP_NOT_CAPTAIN.name()));
        test.andExpect(jsonPath("reason").value(GROUP_NOT_CAPTAIN.getErrorMessage()));
    }
}
