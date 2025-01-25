package com.reborn.server.domain.community.dao;

import com.reborn.server.domain.community.domain.CommunityPost;
import com.reborn.server.domain.community.domain.PostLike;
import com.reborn.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserAndPost(User user, CommunityPost post);
    void deleteByUserAndPost(User user, CommunityPost post);
    Long countAllByPost(CommunityPost post);
}
