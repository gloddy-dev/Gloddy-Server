package com.gloddy.server.acceptance.group;

import com.gloddy.server.common.group.GroupApiTest;
import com.gloddy.server.group.domain.Group;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class GetParticipatedGroupTest extends GroupApiTest {

    @Test
    @DisplayName("success_getParticipatedGroup")
    void successGeParticipatedGroup() throws Exception {
        //given
        //로그인 된 사용자이다.
        Group mockGroup = createGroup();
        Group expectedGroup = createExpectedGroup();
        Group participatedGroup1 = createParticipatedGroup();
        Group participatedGroup2 = createParticipatedGroup();

        createUserGroup(user, mockGroup);
        createUserGroup(user, expectedGroup);
        createUserGroup(user, participatedGroup1);
        createCompletePraiseUserGroup(user, participatedGroup2);

        //when
        //참여했던 그룹 조회 API를 날린다.
        ResultActions test = mockMvc.perform(
                get("/api/v1/groups/my-participated?page=0&size=5")
                .header("X-AUTH-TOKEN", accessToken));

        //then
        //참여했던 그룹이 조회된다.
        //참여했던 그룹의 칭찬여부 또한 조회된다.
        test.andExpect(jsonPath("totalCount").value(2));
        test.andExpect(jsonPath("currentCount").value(2));
        test.andExpect(jsonPath("currentPage").value(0));
        test.andExpect(jsonPath("currentPage").value(0));
        test.andExpect(jsonPath("totalPage").value(1));
        test.andExpect(jsonPath("contents[0].groupId").value(participatedGroup2.getId()));
        test.andExpect(jsonPath("contents[0].meetDate").value(participatedGroup2.getMeetDate().toString()));
        test.andExpect(jsonPath("contents[0].praised").value(true));
        test.andExpect(jsonPath("contents[1].groupId").value(participatedGroup1.getId()));
        test.andExpect(jsonPath("contents[1].meetDate").value(participatedGroup1.getMeetDate().toString()));
        test.andExpect(jsonPath("contents[1].praised").value(false));
    }
}
