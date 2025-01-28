package com.reborn.server.domain.post.domain;

import com.reborn.server.domain.comment.domain.Comment;
import com.reborn.server.domain.post.dto.request.PostRequest;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 사용자와의 다대일 관계
    @JoinColumn(name = "user_id") // 외래 키 컬럼 이름
    private User author; // User 엔티티로 변경

    private String title;
    private String content;
    private String region;

    @Column(name = "post_image")
    private String postImage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "likes_count")
    private Long likesCount = 0L;

    @Column(name = "comments_count")
    private int commentsCount = 0; // 게시물에 달린 총 댓글 수

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Builder
    public Post(User author, String title, String content, String region, String postImage) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.region = region;
        this.postImage = postImage;
    }

    public static Post of (User author, String title, String content, String region, String postImage) {
        return Post.builder()
                .author(author)
                .title(title)
                .content(content)
                .region(region)
                .postImage(postImage)
                .build();
    }

    public static Post from (PostRequest request, User author) {
        return Post.builder()
                .author(author)
                .title(request.getTitle())
                .content(request.getContent())
                .region(request.getRegion())
                .postImage(request.getPostImage())
                .build();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void updatePost (String title, String content, String region, String postImage) {
        this.title = title;
        this.content = content;
        this.region = region;
        this.postImage = postImage;
    }

    // 하나의 포스트에 여러 개의 댓글
    // 포스트 로드 될 때 관련 댓글도 로드됨 & 포스트 삭제 시 관련 댓글도 삭제
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;
  
    public void deletePost() {
        this.isDeleted = true;
    }


    public void setCommentCounts(int commentCounts) {
        this.commentsCount = commentCounts;
    }
  
    public void updateLikesCount (Long likesCount) {
        this.likesCount = likesCount;

    }
}
