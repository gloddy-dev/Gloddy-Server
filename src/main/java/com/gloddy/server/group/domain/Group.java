package com.gloddy.server.group.domain;

import com.gloddy.server.apply.domain.Apply;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.group.domain.vo.GroupDateTime;
import com.gloddy.server.group.domain.vo.GroupMemberVOs;
import com.gloddy.server.group.domain.vo.GroupPlace;
import com.gloddy.server.group.domain.vo.GroupMemberVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
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
    private User captain;

    @Column(name = "school")
    private String school;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    @Embedded
    private GroupDateTime dateTime;

    @Embedded
    private GroupPlace place;

    @Column(name = "max_user")
    private int maxUser;

    @Embedded
    private GroupMemberVOs groupMemberVOs = GroupMemberVOs.empty();

    @Builder
    public Group(User captain, String imageUrl, String title, String content, GroupDateTime dateTime,
                 GroupPlace place, int maxUser, String school) {
        this.captain = captain;
        this.imageUrl = imageUrl;
        this.title = title;
        this.content = content;
        this.dateTime = dateTime;
        this.place = place;
        this.maxUser = maxUser;
        this.school = school;
    }

    public void addUserGroupVOs(GroupMemberVO groupMemberVO) {
        this.groupMemberVOs.addUserGroupVo(groupMemberVO);
    }

    public LocalDate getMeetDate() {
        return this.getDateTime().getStartDateTime().toLocalDate();
    }

    public LocalDateTime getStartDateTime() {
        return this.dateTime.getStartDateTime();
    }

    public LocalDateTime getEndDateTime() {
        return this.dateTime.getEndDateTime();
    }

    public int getMemberCount() {
        return this.groupMemberVOs.getSize();
    }

    public Apply createApply(User applier, String introduce, String reason) {
        return Apply.builder()
                .user(applier)
                .group(this)
                .content(introduce)
                .reason(reason)
                .build();

    }
}
