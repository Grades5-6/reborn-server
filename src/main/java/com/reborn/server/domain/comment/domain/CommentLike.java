package com.reborn.server.domain.comment.domain;

import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(
        name = "comment_like",
        // 중복 좋아요 방지 위해 데이터베이스 상에서 제약 조건 설계
        // 한 유저는 한 댓글에 한 개의 좋아요만 표시 가능
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "comment_id"})
        }
)
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public static CommentLike of(User user, Comment comment) {
        return CommentLike.builder()
                .comment(comment)
                .user(user)
                .build();
    }
}
