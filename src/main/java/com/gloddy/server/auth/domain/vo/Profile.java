package com.gloddy.server.auth.domain.vo;

import com.gloddy.server.auth.domain.vo.kind.Gender;
import com.gloddy.server.auth.domain.vo.kind.Personality;
import com.gloddy.server.core.converter.EnumArrayConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static lombok.Builder.*;

@Getter
@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

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

    @Convert(converter = EnumArrayConverter.class)
    @Default
    @Column(name = "personality")
    private List<Personality> personalities = new ArrayList<>();
}
