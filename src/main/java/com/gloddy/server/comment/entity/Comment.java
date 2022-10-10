package com.gloddy.server.comment.entity;

import com.gloddy.server.article.entity.Article;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @OneToOne(fetch = FetchType.LAZY)
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
}
