package com.reborn.server.domain.community.dao;

import com.reborn.server.domain.community.domain.PostCategoryTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryTagRepository extends JpaRepository<PostCategoryTag, Long> {
}
