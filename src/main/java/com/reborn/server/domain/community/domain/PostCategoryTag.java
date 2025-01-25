package com.reborn.server.domain.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategoryTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoryTag")
    private CategoryTag categoryTag;

    @Builder
    public PostCategoryTag(CommunityPost post, CategoryTag categoryTag) {
        this.post = post;
        this.categoryTag = categoryTag;
    }

    public static PostCategoryTag of (CommunityPost post, CategoryTag categoryTag){
        return PostCategoryTag.builder()
                .post(post)
                .categoryTag(categoryTag)
                .build();
    }

}