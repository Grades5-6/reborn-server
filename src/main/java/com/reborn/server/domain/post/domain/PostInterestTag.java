package com.reborn.server.domain.post.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostInterestTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "interestTag")
    private InterestTag interestTag;

    @Builder
    public PostInterestTag(Post post, InterestTag interestTag) {
        this.post = post;
        this.interestTag = interestTag;
    }

    public static PostInterestTag of(Post post, InterestTag interestTag) {
        return PostInterestTag.builder()
                .post(post)
                .interestTag(interestTag)
                .build();
    }

}
