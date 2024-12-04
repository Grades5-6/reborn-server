package com.reborn.server.domain.community.dao;

import com.reborn.server.domain.community.domain.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Long> {
}