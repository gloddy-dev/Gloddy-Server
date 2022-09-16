package com.gloddy.server.article.entity;

import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.group.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "article")
public class Article extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    @Column(name = "is_notice")
    private boolean isNotice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @Builder
    public Article(String content, boolean isNotice, User user, Group group) {
        this.content = content;
        this.isNotice = isNotice;
        this.user = user;
        this.group = group;
    }

    public void update(String content, boolean isNotice) {
        this.content = content;
        this.isNotice = isNotice;
    }
}
