package com.gloddy.server.acceptance.group;

import com.gloddy.server.common.group.GroupApiTest;
import com.gloddy.server.group.domain.Group;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.gloddy.server.core.utils.DateTimeUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetExpectedGroupTest extends GroupApiTest {

    @Test
    @DisplayName("success_getExpetedGroup")
    void successGetExpectedGroup() throws Exception {
        //given
        //로그인된 사용자이다.
        Group mockGroup = createGroup();
        Group expectedGroup = createExpectedGroup();
        Group participatedGroup = createParticipatedGroup();

        createUserGroup(user, mockGroup);
        createUserGroup(user, expectedGroup);
        createUserGroup(user, participatedGroup);

        //when
        //참여할 그룹 조회 API를 날린다.
        ResultActions test = mockMvc.perform(
                get("/api/v1/groups/my-expected")
                .header("X-AUTH-TOKEN", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
        );
        //then
        //참여할 그룹이 조회된다.
        test.andExpect(status().isOk());
        test.andExpect(jsonPath("groups.size()").value(1));
        test.andExpect(jsonPath("groups[0].groupId").value(expectedGroup.getId()));
        test.andExpect(jsonPath("groups[0].meetDate").value(dateToStringForGroupPreview(expectedGroup.getMeetDate())));
    }

}
