package com.gloddy.server.article.domain;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.comment.domain.Comment;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.group.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "notice")
    private boolean notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Article(String content, boolean notice, User user, Group group) {
        this.content = content;
        this.notice = notice;
        this.user = user;
        this.group = group;
    }

    public void update(String content, boolean notice) {
        this.content = content;
        System.out.println("isNotice = " + notice);
        this.notice = notice;
    }
}
