package com.reborn.server.domain.community.dao;

import com.reborn.server.domain.community.domain.CategoryTag;
import com.reborn.server.domain.community.domain.PostCategoryTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostCategoryTagRepository extends JpaRepository<PostCategoryTag, Long> {
    @Query("SELECT pct.categoryTag FROM PostCategoryTag pct WHERE pct.post.id = :postId")
    List<CategoryTag> findCategoryTagsByPostId(Long postId);

}
