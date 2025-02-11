package com.reborn.server.domain.post.domain;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoryTag")
    private CategoryTag categoryTag;

    @Builder
    public PostCategoryTag(Post post, CategoryTag categoryTag) {
        this.post = post;
        this.categoryTag = categoryTag;
    }

    public static PostCategoryTag of (Post post, CategoryTag categoryTag){
        return PostCategoryTag.builder()
                .post(post)
                .categoryTag(categoryTag)
                .build();
    }

}