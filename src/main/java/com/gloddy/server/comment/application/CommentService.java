package com.gloddy.server.comment.application;

import com.gloddy.server.article.domain.Article;
import com.gloddy.server.article.domain.handler.ArticleQueryHandler;
import com.gloddy.server.auth.domain.User;
import com.gloddy.server.comment.domain.handler.CommentCommandHandler;
import com.gloddy.server.comment.domain.handler.CommentQueryHandler;
import com.gloddy.server.comment.domain.service.CommentDeletePolicy;
import com.gloddy.server.comment.domain.service.CommentDtoMapper;
import com.gloddy.server.group_member.domain.GroupMember;
import com.gloddy.server.group_member.domain.handler.GroupMemberQueryHandler;
import com.gloddy.server.user.domain.handler.UserQueryHandler;
import com.gloddy.server.comment.domain.dto.CommentRequest;
import com.gloddy.server.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.gloddy.server.comment.domain.dto.CommentResponse.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentCommandHandler commentCommandHandler;
    private final CommentQueryHandler commentQueryHandler;
    private final UserQueryHandler userQueryHandler;
    private final ArticleQueryHandler articleQueryHandler;
    private final GroupMemberQueryHandler groupMemberQueryHandler;
    private final CommentDeletePolicy commentDeletePolicy;

    @Transactional
    public Create create(Long userId, Long articleId, CommentRequest.Create request) {
        User user = userQueryHandler.findById(userId);
        Article article = articleQueryHandler.findById(articleId);

        Comment newComment = commentCommandHandler.save(
                article.createComment(user, request.getContent())
        );
        return new Create(newComment.getId());
    }

    @Transactional
    public void delete(Long groupId, Long articleId, Long commentId, Long userId) {
        GroupMember groupMember = groupMemberQueryHandler.findByUserIdAndGroupId(userId, groupId);
        Comment comment = commentQueryHandler.findById(commentId);

        commentDeletePolicy.validate(groupMember, comment);
        commentCommandHandler.delete(comment);
    }

    @Transactional(readOnly = true)
    public GetComments getComments(Long articleId, Long userId) {
        Article article = articleQueryHandler.findById(articleId);
        List<Comment> comments = commentQueryHandler.findAllByArticleFetchUser(article);

        List<GetComment> getComments = CommentDtoMapper.mapToGetCommentListFrom(comments, userId);
        return new GetComments(getComments);
    }
}
