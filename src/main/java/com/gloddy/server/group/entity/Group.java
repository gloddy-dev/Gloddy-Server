package com.gloddy.server.group.entity;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
public class Group extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "captain_id")
    private User user;

    @Column(name = "school")
    private String school;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    @Column(name = "meet_date")
    private LocalDate meetDate;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "place")
    private String place;

    @Column(name = "place_latitude")
    private String placeLatitude;

    @Column(name = "place_longitude")
    private String placeLongitude;

    @Column(name = "max_user")
    private int maxUser;
}
