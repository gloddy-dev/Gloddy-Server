package com.gloddy.server.article.domain.service;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.dto.ArticleResponse;
import com.gloddy.server.article.domain.dto.ImageDto;
import com.gloddy.server.core.utils.DateTimeUtils;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class ArticleDtoMapper {

    public static Page<ArticleResponse.GetArticle> mapToGetArticlePageFrom(Page<Article> articles) {
        return articles.map(
                article -> new ArticleResponse.GetArticle(
                        article.getUser().getImageUrl(),
                        article.getUser().getName(),
                        DateTimeUtils.dateTimeToString(article.getCreatedAt()),
                        article.getContent(),
                        article.isNotice(),
                        article.getCommentCount(),
                        getImageDtos(article)
                ));
    }

    private static List<ImageDto> getImageDtos(Article article) {
        return article.getImages()
                .stream()
                .map(image -> new ImageDto(image.getUrl()))
                .collect(Collectors.toUnmodifiableList());
    }

}
