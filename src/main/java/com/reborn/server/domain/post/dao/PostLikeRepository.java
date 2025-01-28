package com.reborn.server.domain.post.dao;

import com.reborn.server.domain.post.domain.Post;
import com.reborn.server.domain.post.domain.PostLike;
import com.reborn.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserAndPost(User user, Post post);
    void deleteByUserAndPost(User user, Post post);
    Long countAllByPost(Post post);
}
