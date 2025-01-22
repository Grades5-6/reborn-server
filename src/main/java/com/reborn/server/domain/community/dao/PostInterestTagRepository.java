package com.reborn.server.domain.community.dao;

import com.reborn.server.domain.community.domain.InterestTag;
import com.reborn.server.domain.community.domain.PostInterestTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostInterestTagRepository extends JpaRepository<PostInterestTag, Long> {
    @Query("SELECT pit.interestTag FROM PostInterestTag pit WHERE pit.post.id = :postId")
    List<InterestTag> findInterestTagsByPostId(Long postId);
}
