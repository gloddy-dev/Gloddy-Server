package com.gloddy.server.comment.service;

import com.gloddy.server.article.dto.ArticleResponse;
import com.gloddy.server.article.entity.Article;
import com.gloddy.server.article.handler.ArticleHandler;
import com.gloddy.server.article.handler.ArticleHandlerImpl;
import com.gloddy.server.article.repository.ArticleJpaRepository;
import com.gloddy.server.auth.entity.User;
import com.gloddy.server.auth.handler.UserHandlerImpl;
import com.gloddy.server.comment.dto.CommentRequest;
import com.gloddy.server.comment.dto.CommentResponse;
import com.gloddy.server.comment.entity.Comment;
import com.gloddy.server.comment.handler.CommentHandlerImpl;
import com.gloddy.server.comment.repository.CommentJpaRepository;
import com.gloddy.server.core.error.handler.errorCode.ErrorCode;
import com.gloddy.server.core.error.handler.exception.UserBusinessException;
import com.gloddy.server.group.entity.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.comment.dto.CommentResponse.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentJpaRepository commentJpaRepository;
    private final CommentHandlerImpl commentHandlerImpl;
    private final UserHandlerImpl userHandlerImpl;
    private final ArticleHandlerImpl articleHandlerImpl;

    @Transactional
    public Create create(Long userId, Long articleId, CommentRequest.Create request) {
        User user = userHandlerImpl.findById(userId);
        Article article = articleHandlerImpl.findById(articleId);
        Comment comment = commentJpaRepository.save(Comment.builder()
                .user(user)
                .article(article)
                .content(request.getContent())
                .build()
        );
        return new Create(comment.getId());
    }

    @Transactional
    public void delete(Long commentId, Long userId, Long articleId) {
        User user = userHandlerImpl.findById(userId);
        Article article = articleHandlerImpl.findById(articleId);

        Group group = article.getGroup();
        Comment comment = commentHandlerImpl.findById(commentId);

        if(checkCommentUser(comment, group, user)) {
            throw new UserBusinessException(ErrorCode.COMMENT_USER_MISMATCH);
        }
        commentJpaRepository.delete(comment);
    }

    private boolean checkCommentUser(Comment comment, Group group, User user) {
        return comment.getUser().equals(user) || group.getUser().equals(user);
    }

    @Transactional(readOnly = true)
    public GetComments getComments(Long articleId, Long userId) {
        Article article = articleHandlerImpl.findById(articleId);
        User user = userHandlerImpl.findById(userId);
        List<GetComment> comments = commentJpaRepository.findAllByArticle(article)
                .stream()
                .map(comment -> generateCommentDto(comment, user))
                .collect(Collectors.toList());
        return new GetComments(comments);
    }

    private GetComment generateCommentDto(Comment comment, User user) {
        return new GetComment(
                comment.getUser().getImageUrl(),
                comment.getUser().getName(),
                formatDate(comment.getCreatedAt()),
                comment.getContent(),
                comment.getUser().equals(user)
        );
    }

    public int getCommentCount(Article article) {
        return commentJpaRepository.countAllByArticle(article);
    }

    private String formatDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
