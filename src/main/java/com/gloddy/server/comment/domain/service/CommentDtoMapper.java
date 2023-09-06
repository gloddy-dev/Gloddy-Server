package com.gloddy.server.comment.domain.service;

import com.gloddy.server.auth.domain.User;
import com.gloddy.server.comment.domain.Comment;
import com.gloddy.server.core.utils.DateTimePatternConstants;

import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.comment.domain.dto.CommentResponse.*;
import static com.gloddy.server.core.utils.DateTimeUtils.*;

public class CommentDtoMapper {

    public static List<GetComment> mapToGetCommentListFrom(List<Comment> comments, User user) {
        return comments.stream()
                .map(comment -> getComment(comment, user))
                .collect(Collectors.toUnmodifiableList());
    }

    private static GetComment getComment(Comment comment, User user) {
        return new GetComment(
                comment.getUser().getId(),
                comment.getId(),
                comment.getWriterImageUrl(),
                comment.getWriterNickName(),
                toStringDateTime(comment),
                comment.getContent(),
                comment.isWriter(user),
                comment.isWriterGroupCaptain(),
                comment.isWriterCertifiedStudent(),
                comment.getWriterReliabilityLevel().name()
        );
    }

    private static String toStringDateTime(Comment comment) {
        return dateTimeToString(
                comment.getCreatedAt(),
                DateTimePatternConstants.COMMENT
        );
    }
}
