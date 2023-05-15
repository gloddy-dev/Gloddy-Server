package com.gloddy.server.auth.domain;

import com.gloddy.server.auth.domain.vo.kind.Authority;
import com.gloddy.server.auth.domain.vo.kind.Gender;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.core.converter.EnumArrayConverter;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.estimate.domain.Praise;
import com.gloddy.server.reliability.domain.Reliability;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.*;
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

    private String email;

    @Column(name = "image_url", columnDefinition = "longtext")
    private String imageUrl;

    @Column(name = "password", columnDefinition = "longtext")
    private String password;

    private String name;

    @Column(name = "school")
    private String school;

    private LocalDate birth;

    private String introduce;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Convert(converter = EnumArrayConverter.class)
    @Column(name = "personality")
    private List<Personality> personalities = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Reliability reliability;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private Praise praise;

    @Builder
    public User(String email, String password, String name, String school, LocalDate birth, Gender gender, List<Personality> personalities) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.school = school;
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
}
