package com.reborn.server.domain.comment.domain;

import com.reborn.server.domain.post.domain.Post;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String text;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int commentLikes = 0; // 댓글 좋아요 수
//    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
//    private int likes = new ArrayList<>();

    private boolean isDeleted = false; // 댓글 삭제 여부

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_date")
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment")
    private Comment parent; // 부모 댓글 -> 없으면 null
    private int depth; // 댓글 깊이
    private long groupNum; // 댓글 순서

    @OneToMany(mappedBy = "parent") // 하나의 부모에 여러 개의 자식 댓글 존재 가능
    private List<Comment> childrenComment = new ArrayList<>(); // 자식 댓글들 리스트

    public static Comment of(Post post, User user, String text, Comment parent, long groupNum){
        return Comment.builder()
                .post(post)
                .user(user)
                .parent(parent)
                .depth(parent == null ? 0 : 1) // 부모 댓글이면 depth=0, 대댓글이면 1
                .text(text)
                .groupNum(groupNum)
                .build();
    }

    public void setText(String text) {
        this.text= text;
        this.modifiedAt = LocalDateTime.now();
    }

    public void softDelete(boolean deleted) {
        this.isDeleted = deleted;
        // this.text = "삭제된 댓글입니다.";
    }

    public boolean areAllChildrenDeleted() {
        // 자식 댓글이 모두 삭제 상태인지 확인
        return childrenComment.stream().allMatch(Comment::isDeleted);
    }

    public void setCommentLike(int commentLikes) {
       this.commentLikes = commentLikes;
    }
}
