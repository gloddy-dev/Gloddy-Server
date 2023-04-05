package com.gloddy.server.group.entity;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.group.entity.embedded.UserGroups;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "`group`")
public class Group extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "captain_id")
    private User user;

    @Column(name = "school")
    private String school;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "place")
    private String place;

    @Column(name = "place_latitude")
    private String placeLatitude;

    @Column(name = "place_longitude")
    private String placeLongitude;

    @Column(name = "max_user")
    private int maxUser;

    @Embedded
    private UserGroups userGroups = UserGroups.empty();

    @Builder
    public Group(User user, String fileUrl, String title, String content, LocalDateTime startTime, LocalDateTime endTime,
                 String place, String placeLatitude, String placeLongitude, int maxUser, String school) {
        this.user = user;
        this.fileUrl = fileUrl;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.maxUser = maxUser;
        this.school = school;
    }

    public void addUserGroup(UserGroup userGroup) {
        this.userGroups.addUserGroups(userGroup);
    }

    public LocalDate getMeetDate() {
        return this.getStartTime().toLocalDate();
    }

    public int getMemberCount() {
        return this.userGroups.getSize();
    }
}
