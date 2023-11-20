package com.gloddy.server.acceptance.groupMember;

import com.gloddy.server.user.domain.User;
import com.gloddy.server.common.group.GroupApiTest;
import com.gloddy.server.group.domain.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetAllGroupMemberTest extends GroupApiTest {

    private User captainUser;
    private User generalUser;
    private Group group;

    @BeforeEach
    void given() {
        captainUser = createUser();
        generalUser = createUser();

        group = createGroup(captainUser);
        createGroupMember(captainUser, group);
        createGroupMember(generalUser, group);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("그룹 멤버 전체 조회 - success")
    void successGetAllGroupMembers() throws Exception {

        ResultActions test = mockMvc.perform(
                get("/api/v1/groups" + "/" + group.getId() + "/members")
                .header("X-AUTH-TOKEN", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );

        User findCaptainUser = userJpaRepository.findById(captainUser.getId()).orElseThrow();
        User findGeneralUser = userJpaRepository.findById(generalUser.getId()).orElseThrow();

        test.andExpect(status().isOk());
        test.andExpect(jsonPath("groupMembers.size()").value(2));
        test.andExpect(jsonPath("groupMembers[0].isCaptain").value(true));
        test.andExpect(jsonPath("groupMembers[0].isCertifiedStudent").value(false));
        test.andExpect(jsonPath("groupMembers[0].userId").value(findCaptainUser.getId()));
        test.andExpect(jsonPath("groupMembers[0].reliabilityLevel").value(findCaptainUser.getReliability().getLevel().toString()));
        test.andExpect(jsonPath("groupMembers[1].isCaptain").value(false));
        test.andExpect(jsonPath("groupMembers[1].isCertifiedStudent").value(false));
        test.andExpect(jsonPath("groupMembers[1].userId").value(findGeneralUser.getId()));
        test.andExpect(jsonPath("groupMembers[1].reliabilityLevel").value(findGeneralUser.getReliability().getLevel().toString()));
    }
}
