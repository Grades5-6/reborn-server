package com.reborn.server.domain.post.dao;

import com.reborn.server.domain.post.domain.CategoryTag;
import com.reborn.server.domain.post.domain.PostCategoryTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostCategoryTagRepository extends JpaRepository<PostCategoryTag, Long> {
    @Query("SELECT pct.categoryTag FROM PostCategoryTag pct WHERE pct.post.id = :postId")
    List<CategoryTag> findCategoryTagsByPostId(Long postId);

    @Query("SELECT pct FROM PostCategoryTag pct WHERE pct.categoryTag = :categoryTag AND pct.post.id = :postId")
    Optional<PostCategoryTag> findByCategoryTagAndPostId(@Param("categoryTag") CategoryTag categoryTag, @Param("postId") Long postId);

}
