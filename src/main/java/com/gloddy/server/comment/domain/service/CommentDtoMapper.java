package com.gloddy.server.comment.domain.service;


import com.gloddy.server.auth.domain.User;
import com.gloddy.server.auth.domain.vo.Profile;
import com.gloddy.server.comment.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

import static com.gloddy.server.comment.domain.dto.CommentResponse.*;
import static com.gloddy.server.core.utils.DateTimeUtils.*;

public class CommentDtoMapper {

    public static List<GetComment> mapToGetCommentListFrom(List<Comment> comments, Long userId) {
        return comments.stream()
                .map(comment -> getComment(comment, userId))
                .collect(Collectors.toUnmodifiableList());
    }

    private static GetComment getComment(Comment comment, Long userId) {
        User user = comment.getUser();
        Profile profile = user.getProfile();

        return new GetComment(
                profile.getImageUrl(),
                profile.getNickname(),
                dateTimeToString(comment.getCreatedAt()),
                comment.getContent(),
                user.getId().equals(userId)
        );
    }
}
