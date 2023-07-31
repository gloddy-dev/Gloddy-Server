package com.gloddy.server.auth.domain;

import com.gloddy.server.auth.domain.vo.Profile;
import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.domain.vo.School;
import com.gloddy.server.auth.domain.vo.kind.Authority;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.core.event.GroupParticipateEvent;
import com.gloddy.server.group.event.GroupCreateEvent;
import com.gloddy.server.group.event.producer.GroupEventProducer;
import com.gloddy.server.praise.domain.Praise;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.handler.GroupCommandHandler;
import com.gloddy.server.group.domain.service.GroupFactory;
import com.gloddy.server.reliability.domain.Reliability;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Authority authority;

    @Embedded
    private Phone phone;

    @Embedded
    private School school;

    @Embedded
    private Profile profile;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Reliability reliability;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Praise praise;

    @Builder
    public User(Phone phone, School school, Profile profile) {
        this.phone = phone;
        this.school = school;
        this.profile = profile;
        authorityDefault();
    }

    private void authorityDefault() {
        this.authority = Authority.USER;
    }

    public void setPraise(Praise praise) {
        this.praise = praise;
    }

    public Group saveGroup(GroupFactory groupFactory, GroupEventProducer groupEventProducer,
                           GroupCommandHandler groupCommandHandler, GroupRequest.Create groupInfo) {
        Group group = groupCommandHandler.save(groupFactory.getGroupFrom(this, groupInfo));

        groupEventProducer.produceEvent(new GroupCreateEvent(this.getId()));
        groupEventProducer.produceEvent(new GroupParticipateEvent(this.getId(), group.getId()));
        return group;
    }

    public String getSchool() {
        return this.school.getSchool();
    }
}
