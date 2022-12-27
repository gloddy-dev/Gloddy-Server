package com.gloddy.server.article.service;

import com.gloddy.server.article.dto.ArticleRequest;
import com.gloddy.server.article.entity.Article;
import com.gloddy.server.article.handler.ArticleHandler;
import com.gloddy.server.article.repository.ArticleJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.handler.UserHandler;
import com.gloddy.server.comment.entity.Comment;
import com.gloddy.server.comment.repository.CommentJpaRepository;
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
import java.util.stream.Collectors;

import static com.gloddy.server.article.dto.ArticleResponse.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleJpaRepository articleJpaRepository;
    private final ArticleHandler articleHandler;
    private final CommentJpaRepository commentJpaRepository;
    private final UserHandler userHandler;
    private final GroupHandler groupHandler;

    @Transactional
    // TODO:게시글에 사진 추가 하는 경우 null값 검사해 null이 아니면 S3에 사진 업로드 후 url DB에 저장
    public Create create(Long groupId, Long userId, ArticleRequest.Create request) {
        User user = userHandler.findById(userId);
        Group group = groupHandler.findById(groupId);

        Article article = Article.builder()
                .content(request.getContent())
                .isNotice(request.isNotice())
                .user(user)
                .group(group)
                .build();
        articleJpaRepository.save(article);
        return new Create(article.getId());
    }

    @Transactional
    public Update update(Long articleId, Long userId, ArticleRequest.Update request) {
        Article article = articleHandler.findById(articleId);
        User user = userHandler.findById(userId);
        checkWriter(article, user);

        article.update(request.getContent(), request.isNotice());
        // TODO: JPA는 더티체킹이 되지 않나? 업데이트 후 save 안해줘도 되지 않나?
        Article updateArticle = articleJpaRepository.save(article);
        return new Update(
                updateArticle.getContent(),
                updateArticle.isNotice()
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
        if (!article.getUser().equals(user) && !group.getUser().equals(user)) {
            throw new ArticleBusinessException(ErrorCode.NO_ARTICLE_DELETE_PERMISSION);
        }
    }

    // TODO: 게시글 사진 추가해야됨
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

    // TODO: 게시글 작성자 이미지 추가 해야됨.
    private GetArticle getArticle(Article article) {
        User writer = article.getUser();
        int commentCount = commentJpaRepository.countAllByArticle(article);

        return new GetArticle(
//            writer.getImageUrl(),
            writer.getName(),
            formatDate(article.getCreatedAt()),
            article.getContent(),
            commentCount
        );
    }

    @Transactional(readOnly = true)
    public GetDetail getDetail(Long articleId) {
        Article article = articleHandler.findById(articleId);
        List<Comment> comments = commentJpaRepository.findAllByArticle(article);
        List<GetComment> responseComments = comments.stream()
            .map(this::getComment)
            .collect(Collectors.toList());

        return new GetDetail(
            getArticle(article),
            responseComments
        );
    }

    private GetComment getComment(Comment comment) {
        return new GetComment(
            comment.getUser().getName(),
            formatDate(comment.getCreatedAt()),
            comment.getContent()
        );
    }

    private String formatDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
