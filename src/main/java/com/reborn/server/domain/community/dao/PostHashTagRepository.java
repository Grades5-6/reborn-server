package com.reborn.server.domain.community.dao;

import com.reborn.server.domain.community.domain.PostInterestTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashTagRepository extends JpaRepository<PostInterestTag, Long> {
}
