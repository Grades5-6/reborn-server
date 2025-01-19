package com.reborn.server.domain.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
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
    public static CommunityPost of (String author, String title, String content, String region, String postImage) {
        return CommunityPost.builder()
                .author(author)
                .title(title)
                .content(content)
                .region(region)
                .postImage(postImage)
                .build();
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
