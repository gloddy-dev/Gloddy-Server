package com.gloddy.server.article.service;

import com.gloddy.server.article.dto.ArticleRequest;
import com.gloddy.server.article.dto.ArticleResponse;
import com.gloddy.server.article.dto.ImageDto;
import com.gloddy.server.article.entity.Article;
import com.gloddy.server.article.handler.ArticleHandler;
import com.gloddy.server.article.repository.ArticleJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.user.handler.UserQueryHandler;
import com.gloddy.server.comment.service.CommentService;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.ArticleBusinessException;
import com.gloddy.server.core.response.PageResponse;
import com.gloddy.server.group.entity.Group;
import com.gloddy.server.group.handler.GroupHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.gloddy.server.article.dto.ArticleResponse.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleJpaRepository articleJpaRepository;
    private final ArticleHandler articleHandler;
    private final UserQueryHandler userHandler;
    private final GroupHandler groupHandler;
    private final ImageService imageService;
    private final CommentService commentService;

    @Transactional
    public ArticleResponse.Create create(Long groupId, Long userId, ArticleRequest.Create request) {
        User user = userHandler.findById(userId);
        Group group = groupHandler.findById(groupId);

        Article article = Article.builder()
                .content(request.getContent())
                .notice(request.isNotice())
                .user(user)
                .group(group)
                .build();
        articleJpaRepository.save(article);
        imageService.create(article, request.getImages());

        return new ArticleResponse.Create(article.getId());
    }

    @Transactional
    public Update update(Long articleId, Long userId, ArticleRequest.Update request) {
        System.out.println("request = " + request.isNotice());
        Article article = articleHandler.findById(articleId);
        User user = userHandler.findById(userId);
        checkWriter(article, user);

        article.update(request.getContent(), request.isNotice());
        Article updateArticle = articleJpaRepository.save(article);
        imageService.delete(article);
        imageService.create(article, request.getImages());

        return new Update(
                updateArticle.getContent(),
                updateArticle.isNotice(),
                imageService.get(article)
        );
    }

    private void checkWriter(Article article, User user) {
        if (!article.getUser().equals(user)) {
            throw new ArticleBusinessException(ErrorCode.NO_ARTICLE_WRITER);
        }
    }

    @Transactional
    public void delete(Long groupId, Long articleId, Long userId) {
        Group group = groupHandler.findById(groupId);
        Article article = articleHandler.findById(articleId);
        User user = userHandler.findById(userId);
        checkPermission(group, article, user);
        articleJpaRepository.delete(article);
    }

    private void checkPermission(Group group, Article article, User user) {
        if (!article.getUser().equals(user) && !group.getCaptain().equals(user)) {
            throw new ArticleBusinessException(ErrorCode.NO_ARTICLE_DELETE_PERMISSION);
        }
    }

    @Transactional(readOnly = true)
    public GetPreview getPreview(Long groupId, int page, int size) {
        Group group = groupHandler.findById(groupId);
        Pageable pageable = PageRequest.of(page, size);
        Page<GetArticle> articles = articleJpaRepository.findAllByGroup(group, pageable)
                .map(this::getArticle);

        return new GetPreview(
            group.getFileUrl(),
            group.getTitle(),
            group.getContent(),
            PageResponse.from(articles)
        );
    }

    private GetArticle getArticle(Article article) {
        User writer = article.getUser();
        int commentCount = commentService.getCommentCount(article);
        List<ImageDto> images = imageService.get(article);

        return new GetArticle(
                writer.getImageUrl(),
                writer.getName(),
                formatDate(article.getCreatedAt()),
                article.getContent(),
                article.isNotice(),
                commentCount,
                images
        );
    }

    private String formatDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
