package com.reborn.server.domain.comment.dto.request;

import com.reborn.server.domain.comment.domain.Comment;
import lombok.*;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// client -> server
public class CommentRequest {
    private String text;
    private Long userId;
    private Long postId;
    private int commentLike;
    private Long parentId;

}
