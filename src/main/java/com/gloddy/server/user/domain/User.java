package com.gloddy.server.user.domain;

import com.gloddy.server.user.domain.vo.*;
import com.gloddy.server.user.domain.vo.kind.Authority;
import com.gloddy.server.user.domain.vo.kind.Gender;
import com.gloddy.server.user.domain.vo.kind.Personality;
import com.gloddy.server.user.domain.vo.kind.Status;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.core.event.GroupParticipateEvent;
import com.gloddy.server.group.event.GroupCreateEvent;
import com.gloddy.server.group.event.producer.GroupEventProducer;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.group.domain.dto.GroupRequest;
import com.gloddy.server.group.domain.handler.GroupCommandHandler;
import com.gloddy.server.group.domain.service.GroupFactory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = false)
@Getter
@Table(name = "users")
@Where(clause = "status = 'ACTIVE'")
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reliability_id")
    private Reliability reliability;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "praise_id")
    private Praise praise;

    @Builder
    public User(Phone phone, School school, Profile profile) {
        this.phone = phone;
        this.school = school;
        this.profile = profile;
        this.status = Status.ACTIVE;
        authorityDefault();
        this.reliability = new Reliability();
        Praise praise = Praise.empty();
        praise.init();
        this.praise = praise;
    }

    private void authorityDefault() {
        this.authority = Authority.USER;
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

    public String getEmail() {
        return this.school.getEmail();
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

    public Country getCountry() {
        Profile profile = this.profile;
        return Optional.ofNullable(profile.getCountry())
                .orElseGet(Country::new);
    }

    public void updateProfile(String imageUrl, String nickname, LocalDate birth,
                              Gender gender, String introduce, List<Personality> personalities,
                              String countryName, String countryImage
    ) {
        this.profile = Profile.builder()
                .imageUrl(imageUrl)
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .introduce(introduce)
                .personalities(personalities)
                .country(new Country(countryName, countryImage))
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

    public void verifyStudent(String email) {
        var school = this.school;
        this.school = School.createCertified(
                school.getSchool(), email
        );
    }

    public void receivePraise(PraiseValue praiseValue) {
        this.praise.plusPraiseCount(praiseValue);
        if (praiseValue.isAbsence()) {
            reflectReliability(ScoreMinusType.Absence_Group);
        } else {
            reflectReliability(ScorePlusType.Praised);
        }
    }

    public void reflectReliability(ScoreTypes scoreTypes) {
        Long reflectScore = scoreTypes.getReflectScore();
        this.reliability.upgrade(reflectScore);
    }
}
