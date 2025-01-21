package com.reborn.server.domain.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private CommunityPost post;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoryTag_id")
    private CategoryTag categoryTag;

    public PostCategoryTag(CommunityPost post, CategoryTag categoryTag) {
        this.post = post;
        this.categoryTag = categoryTag;
    }

}