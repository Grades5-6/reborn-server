package com.reborn.server.domain.comment.dto.response;

import com.reborn.server.domain.comment.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
// server -> client
public class CommentResponse {
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long commentId;
    private String text;
    private boolean isDeleted;
    private int depth;
    private long groupNum;
    private List<CommentResponse> children;  // 자식 댓글 리스트
    private int commentLikesCount;


    public static CommentResponse of(Comment comment) {
        return CommentResponse.builder()
                .userName(comment.getUser().getNickName())
                .commentId(comment.getId())
                .text(comment.getText())
                .isDeleted(comment.isDeleted())
                .commentLikesCount(comment.getCommentLikes())
                .createdAt(LocalDateTime.parse(comment.getCreatedAt().toString()))
                .modifiedAt(LocalDateTime.parse(comment.getModifiedAt().toString()))
                .depth(comment.getDepth())
                .groupNum(comment.getGroupNum())
                .children(comment.getChildrenComment() != null
                        ? comment.getChildrenComment().stream()
                        .map(CommentResponse::of)
                        .collect(Collectors.toList())
                        : new ArrayList<>())
                .build();
    }

}
