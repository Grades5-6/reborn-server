package com.reborn.server.domain.community.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private CommunityPost post;

    @Column(name = "interestTag_id")
    private InterestTag interestTag;

    public PostInterestTag(CommunityPost post, InterestTag interestTag) {
        this.post = post;
        this.interestTag = interestTag;
    }

}
