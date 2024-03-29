package com.gloddy.server.comment.domain;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.user.domain.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import com.gloddy.server.user.domain.vo.ReliabilityLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    @Builder
    public Comment(Article article, User user, String content) {
        this.article = article;
        this.user = user;
        this.content = content;
    }

    public boolean isWriter(User user) {
        return this.user.equals(user);
    }

    public boolean isWriterGroupCaptain() {
        return this.article.getGroup().isCaptain(this.user);
    }

    public boolean isWriterCertifiedStudent() {
        return user.isCertifiedStudent();
    }

    public ReliabilityLevel getWriterReliabilityLevel() {
        return user.getReliabilityLevel();
    }

    public String getWriterImageUrl() {
        return this.user.getImageUrl();
    }

    public String getWriterNickName() {
        return this.user.getNickName();
    }
}
