package com.gloddy.server.common.reliability;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.BaseApiTest;
import com.gloddy.server.group.domain.vo.GroupMemberVO;
import com.gloddy.server.praise.domain.vo.PraiseValue;
import com.gloddy.server.mate.infra.repository.MateJpaRepository;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import com.gloddy.server.group_member.infra.repository.GroupMemberJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.*;
import static com.gloddy.server.group_member.domain.dto.GroupMemberRequest.Estimate.*;

public abstract class ReliabilityApiTest extends BaseApiTest {


    @Autowired
    protected GroupJpaRepository groupJpaRepository;

    @Autowired
    protected GroupMemberJpaRepository groupMemberJpaRepository;

    @Autowired
    protected MateJpaRepository mateJpaRepository;


    protected Group createGroup() {
        Group group = new Group();
        return groupJpaRepository.save(group);
    }

    protected GroupMember createGroupMember(User user, Group group) {
        GroupMember groupMember = GroupMember.empty();
        groupMember.init(user, group);
        GroupMemberVO groupMemberVO = groupMember.createUserGroupVO();
        group.addUserGroupVOs(groupMemberVO);
        return groupMemberJpaRepository.save(groupMember);
    }

    protected GroupRequest.Create createGroupCreateRequest() {
        LocalDate now = LocalDate.now();
        return new GroupRequest.Create(
                "test_FileUrl",
                "test_GroupTitle",
                "test_Content",
                LocalDate.of(now.getYear(), 4, 26),
                "12:00",
                "숭실대학교",
                "동작구 상도로",
                "placeId",
                new BigDecimal(150),
                new BigDecimal(150),
                10
        );
    }

    protected Estimate createEstimateRequest(User user, PraiseValue praiseValue) {
        PraiseInfo praiseDto = new PraiseInfo(user.getId(), praiseValue);
        MateInfo mateDto = new MateInfo(user.getId(), "test");

        return new Estimate(
                List.of(praiseDto),
                mateDto
        );
    }
}
