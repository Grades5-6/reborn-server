package com.reborn.server.domain.community.dao;

import com.reborn.server.domain.community.domain.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
    @Override
    @Query("SELECT p FROM CommunityPost p WHERE p.isDeleted = false")
    List<CommunityPost> findAll();
}