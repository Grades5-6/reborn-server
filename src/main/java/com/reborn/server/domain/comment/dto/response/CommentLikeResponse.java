package com.reborn.server.domain.comment.dto.response;

import com.reborn.server.domain.comment.domain.Comment;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLikeResponse {
    private Long commentId;
    private int likeCounts;

    public static CommentLikeResponse of(Comment comment){
        return CommentLikeResponse.builder()
                .commentId(comment.getId())
                .likeCounts(comment.getCommentLikes())
                .build();
    }
}
