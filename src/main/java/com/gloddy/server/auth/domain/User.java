package com.gloddy.server.auth.domain;

import com.gloddy.server.auth.domain.vo.Phone;
import com.gloddy.server.auth.domain.vo.School;
import com.gloddy.server.auth.domain.vo.kind.Authority;
import com.gloddy.server.auth.domain.vo.kind.Gender;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.core.converter.EnumArrayConverter;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Phone phone;

    @Embedded
    private School school;

    @Column(name = "image_url", columnDefinition = "longtext")
    private String imageUrl;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "birth")
    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "introduce")
    private String introduce;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private Authority authority;

    @Convert(converter = EnumArrayConverter.class)
    @Column(name = "personality")
    private List<Personality> personalities = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Reliability reliability;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Praise praise;

    @Builder
    public User(Phone phone, School school, String imageUrl, String nickname,
                LocalDate birth, Gender gender, List<Personality> personalities) {
        this.phone = phone;
        this.school = school;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
        this.personalities = personalities;
        authorityDefault();
    }

    private void authorityDefault() {
        this.authority = Authority.USER;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }

        User target = (User) obj;

        return Objects.equals(this.id, target.getId());
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
