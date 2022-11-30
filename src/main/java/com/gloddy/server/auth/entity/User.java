package com.gloddy.server.auth.entity;

import com.gloddy.server.auth.entity.kind.Authority;
import com.gloddy.server.auth.entity.kind.Gender;
import com.gloddy.server.auth.entity.kind.Personality;
import com.gloddy.server.auth.entity.score.Score;
import com.gloddy.server.core.converter.EnumArrayConverter;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.estimate.entity.Estimate;
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
@Table(name = "user")
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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Convert(converter = EnumArrayConverter.class)
    @Column(name = "personality")
    private List<Personality> personalities = new ArrayList<>();

    private int score;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estimate_id")
    private Estimate estimate;

    @Builder
    public User(String email, String password, String name, String school, LocalDate birth, Gender gender, List<Personality> personalities) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.school = school;
        this.birth = birth;
        this.gender = gender;
        this.personalities = personalities;
        this.score = 0;
        authorityDefault();
    }

    private void authorityDefault() {
        this.authority = Authority.USER;
    }

    public void updateScore(Score score) {
        this.score += score.getValue();
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

        System.out.println(Hibernate.getClass(this));
        System.out.println(Hibernate.getClass(obj));

        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) {
            return false;
        }

        User target = (User) obj;

        System.out.println(this.id);
        System.out.println(target.getId());

        return Objects.equals(this.id, target.getId());
    }
}
