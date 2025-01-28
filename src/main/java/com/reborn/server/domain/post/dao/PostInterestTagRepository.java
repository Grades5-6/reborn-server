package com.reborn.server.domain.post.dao;

import com.reborn.server.domain.post.domain.InterestTag;
import com.reborn.server.domain.post.domain.PostInterestTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostInterestTagRepository extends JpaRepository<PostInterestTag, Long> {
    @Query("SELECT pit.interestTag FROM PostInterestTag pit WHERE pit.post.id = :postId")
    List<InterestTag> findInterestTagsByPostId(Long postId);

    @Query("SELECT pit FROM PostInterestTag pit WHERE pit.interestTag = :interestTag AND pit.post.id = :postId")
    Optional<PostInterestTag> findByInterestTagAndPostId(@Param("interestTag") InterestTag interestTag, @Param("postId") Long postId);


    void deleteByInterestTag(InterestTag interestTag);
}
