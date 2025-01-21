package com.reborn.server.domain.community.domain;

import com.reborn.server.domain.community.dto.request.CommunityPostRequest;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityPost {

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
    private int likesCount = 0;

    @Column(name = "comments_count")
    private int commentsCount = 0;


    @Builder
    public CommunityPost(User author, String title, String content, String region, String postImage) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.region = region;
        this.postImage = postImage;
    }

    public static CommunityPost of (User author, String title, String content, String region, String postImage) {
        return CommunityPost.builder()
                .author(author)
                .title(title)
                .content(content)
                .region(region)
                .postImage(postImage)
                .build();
    }

    public static CommunityPost from (CommunityPostRequest request, User author) {
        return CommunityPost.builder()
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
}
