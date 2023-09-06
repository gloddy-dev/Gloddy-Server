package com.gloddy.server.auth.domain;

import com.gloddy.server.auth.domain.vo.Profile;
import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.domain.vo.School;
import com.gloddy.server.auth.domain.vo.kind.Authority;
import com.gloddy.server.auth.domain.vo.kind.Gender;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.auth.domain.vo.kind.Status;
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
import com.gloddy.server.reliability.domain.vo.ReliabilityLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Getter
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Authority authority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

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
        this.status = Status.ACTIVE;
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

    public boolean isCertifiedStudent() {
        return this.school.isCertifiedStudent();
    }

    public String getNickName() {
        return this.profile.getNickname();
    }

    public String getImageUrl() {
        return this.profile.getImageUrl();
    }

    public Gender getGender() {
        return this.profile.getGender();
    }

    public String getIntroduce() {
        return this.profile.getIntroduce();
    }

    public List<Personality> getPersonalities() {
        return this.profile.getPersonalities();
    }

    public void updateProfile(String imageUrl, String nickname, LocalDate birth,
                              Gender gender, String introduce, List<Personality> personalities
    ) {
        this.profile = Profile.builder()
                .imageUrl(imageUrl)
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .introduce(introduce)
                .personalities(personalities)
                .build();
    }

    public ReliabilityLevel getReliabilityLevel() {
        return this.reliability.getLevel();
    }

    public LocalDate getBirth() {
        return this.profile.getBirth();
    }

    public LocalDate getJoinAt() {
        return this.getCreatedAt().toLocalDate();
    }

    public void withDraw() {
        this.status = Status.WITHDRAW;
    }
}
