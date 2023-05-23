package com.gloddy.server.comment.domain.service;


import com.gloddy.server.comment.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.comment.domain.dto.CommentResponse.*;
import static com.gloddy.server.core.utils.DateTimeUtils.*;

public class CommentDtoMapper {

    public static List<GetComment> mapToGetCommentListFrom(List<Comment> comments, Long userId) {
        return comments.stream()
                .map(comment -> new GetComment(
                        comment.getUser().getImageUrl(),
                        comment.getUser().getName(),
                        dateTimeToString(comment.getCreatedAt()),
                        comment.getContent(),
                        comment.getUser().getId().equals(userId)
                ))
                .collect(Collectors.toUnmodifiableList());
    }
}
