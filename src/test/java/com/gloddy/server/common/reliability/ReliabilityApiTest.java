package com.gloddy.server.common.reliability;

import com.gloddy.server.common.BaseApiTest;
import com.gloddy.server.estimate.dto.EstimateRequest;
import com.gloddy.server.estimate.dto.MateDto;
import com.gloddy.server.estimate.dto.PraiseDto;
import com.gloddy.server.estimate.entity.embedded.PraiseValue;
import com.gloddy.server.estimate.repository.AbsenceInGroupJpaRepository;
import com.gloddy.server.group.dto.GroupRequest;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.entity.UserGroup;
import com.gloddy.server.group.repository.GroupJpaRepository;
import com.gloddy.server.group.repository.UserGroupJpaRepository;
import com.gloddy.server.reliability.handler.ReliabilityQueryHandler;
import com.gloddy.server.reliability.repository.ReliabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class ReliabilityApiTest extends BaseApiTest {

    @Autowired
    protected ReliabilityQueryHandler reliabilityQueryHandler;

    @Autowired
    protected ReliabilityRepository reliabilityRepository;

    @Autowired
    protected GroupJpaRepository groupJpaRepository;

    @Autowired
    protected UserGroupJpaRepository userGroupJpaRepository;

    @Autowired
    protected AbsenceInGroupJpaRepository absenceInGroupJpaRepository;

//    public Reliability createReliability(User user) {
//        Reliability reliability = new Reliability(user);
//        return reliabilityQueryHandler.save(reliability);
//    }

    public Group createGroup() {
        Group group = new Group();
        return groupJpaRepository.save(group);
    }

    public UserGroup createUserGroup(Group group) {
        UserGroup userGroup = UserGroup.empty();
        userGroup.init(user, group);
        return userGroupJpaRepository.save(userGroup);
    }

    public GroupRequest.Create createGroupCreateRequest() {
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

    public EstimateRequest createEstimateRequest() {
        PraiseDto praiseDto = new PraiseDto(user.getId(), PraiseValue.KIND);
        MateDto mateDto = new MateDto(user.getId(), "test");

        return new EstimateRequest(
                List.of(praiseDto),
                mateDto
        );
    }
}
