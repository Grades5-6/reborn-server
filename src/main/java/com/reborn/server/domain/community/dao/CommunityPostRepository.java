package com.reborn.server.domain.community.dao;

import com.reborn.server.domain.community.domain.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    @Override
    @Query("SELECT p FROM CommunityPost p WHERE p.isDeleted = false")
    List<CommunityPost> findAll();

    // isDeleted = false 인 애들만 찾아서 출력
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.isDeleted = false AND c.post.id = :postId")
    int countNotDeletedByPostId(@Param("postId") Long postId);
}