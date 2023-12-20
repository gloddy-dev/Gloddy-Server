package com.gloddy.server.group.domain.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "group_member_vo", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "group_id"}))
public class GroupMemberVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Builder
    public GroupMemberVO(Long userId) {
        this.userId = userId;
    }

    public boolean userIdEq(Long userId) {
        return this.userId.equals(userId);
    }
}
