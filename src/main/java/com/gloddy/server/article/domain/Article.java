package com.gloddy.server.article.domain;

import com.gloddy.server.article.domain.vo.ArticleImage;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.comment.domain.Comment;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.group.domain.Group;
import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        this.notice = notice;
    }

    public void createAndAddAllArticleImages(List<String> images) {
        List<ArticleImage> articleImages = images.stream()
                .map(image -> new ArticleImage(this, image))
                .collect(Collectors.toList());
        this.images.addAll(articleImages);
    }

    public void upsertArticleImages(List<String> images) {
        List<ArticleImage> articleImages = images.stream()
                .map(image -> new ArticleImage(this, image))
                .collect(Collectors.toList());
        this.images.clear();
        this.images.addAll(articleImages);
    }

    public boolean isWriter(User user) {
        return user.equals(this.user);
    }

    public int getCommentCount() {
        return this.comments.size();
    }

    public Comment createComment(User user, String content) {
        return Comment.builder()
                .article(this)
                .user(user)
                .content(content)
                .build();
    }

    public boolean isWriterGroupCaptain() {
        return group.isCaptain(this.user);
    }

    public boolean isWriterCertifiedStudent() {
        return user.isCertifiedStudent();
    }

    public ReliabilityLevel getWriterReliabilityLevel() {
        return user.getReliabilityLevel();
    }
}
