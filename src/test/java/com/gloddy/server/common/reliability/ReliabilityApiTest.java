package com.gloddy.server.common.reliability;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.common.BaseApiTest;
import com.gloddy.server.estimate.domain.dto.EstimateRequest;
import com.gloddy.server.estimate.domain.dto.MateDto;
import com.gloddy.server.estimate.domain.dto.PraiseDto;
import com.gloddy.server.estimate.domain.vo.PraiseValue;
import com.gloddy.server.estimate.infra.repository.MateJpaRepository;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.UserGroup;
import com.gloddy.server.group.infra.repository.GroupJpaRepository;
import com.gloddy.server.group.infra.repository.UserGroupJpaRepository;
import com.gloddy.server.reliability.domain.Reliability;
import com.gloddy.server.reliability.domain.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.infra.repository.ReliabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public abstract class ReliabilityApiTest extends BaseApiTest {

    @Autowired
    protected ReliabilityQueryHandler reliabilityQueryHandler;

    @Autowired
    protected ReliabilityRepository reliabilityRepository;

    @Autowired
    protected GroupJpaRepository groupJpaRepository;

    @Autowired
    protected UserGroupJpaRepository userGroupJpaRepository;

    @Autowired
    protected MateJpaRepository mateJpaRepository;


    protected Group createGroup() {
        Group group = new Group();
        return groupJpaRepository.save(group);
    }

    protected UserGroup createUserGroup(User user, Group group) {
        UserGroup userGroup = UserGroup.empty();
        userGroup.init(user, group);
        return userGroupJpaRepository.save(userGroup);
    }

    protected Reliability createReliability(User user) {
        Reliability reliability = new Reliability(user);
        return reliabilityQueryHandler.save(reliability);
    }

    protected GroupRequest.Create createGroupCreateRequest() {
        LocalDate now = LocalDate.now();
        return new GroupRequest.Create(
                "test_FileUrl",
                "test_GroupTitle",
                "test_Content",
                LocalDate.of(now.getYear(), 4, 26),
                "12:00",
                "15:00",
                "숭실대학교",
                "150",
                "150",
                10
        );
    }

    protected EstimateRequest createEstimateRequest(User user, PraiseValue praiseValue) {
        PraiseDto praiseDto = new PraiseDto(user.getId(), praiseValue);
        MateDto mateDto = new MateDto(user.getId(), "test");

        return new EstimateRequest(
                List.of(praiseDto),
                mateDto
        );
    }
}
