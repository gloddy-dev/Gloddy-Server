package com.gloddy.server.article.domain.vo;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.core.entity.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_image")
public class ArticleImage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    private String url;

    public ArticleImage(Article article, String url) {
        this.article = article;
        this.url = url;
    }
}
