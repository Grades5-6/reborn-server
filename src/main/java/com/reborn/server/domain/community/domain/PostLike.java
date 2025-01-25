package com.reborn.server.domain.community.domain;

import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private CommunityPost post;

    @Builder
    public PostLike(User user, CommunityPost post) {
        this.user = user;
        this.post = post;
    }

    public static PostLike of(User user, CommunityPost post) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .build();
    }
}
