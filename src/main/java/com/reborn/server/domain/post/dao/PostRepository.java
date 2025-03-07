package com.reborn.server.domain.post.dao;

import com.reborn.server.domain.post.domain.CategoryTag;
import com.reborn.server.domain.post.domain.InterestTag;
import com.reborn.server.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    @Query("SELECT p FROM Post p WHERE p.isDeleted = false")
    List<Post> findAll();

    // isDeleted = false 인 애들만 찾아서 출력
    @Query("SELECT COUNT(c) FROM Comment c WHERE c.isDeleted = false AND c.post.id = :postId")
    int countNotDeletedByPostId(@Param("postId") Long postId);

    @Query("SELECT p FROM Post p WHERE p.isDeleted = false")
    List<Post> findAllNotDeleted();

    @Query("SELECT DISTINCT p FROM Post p " +
            "JOIN p.postInterestTags it " +
            "LEFT JOIN p.postCategoryTags ct " +
            "WHERE p.isDeleted = false "+
            "AND (:interestTag IS NULL OR it.interestTag = :interestTag) " +
            "AND (:categoryTag IS NULL OR ct.categoryTag = :categoryTag)")
    List<Post> findPostsByInterestTagAndCategoryTag(@Param("interestTag") InterestTag interestTag,
                                                    @Param("categoryTag") CategoryTag categoryTag);

    @Query("SELECT DISTINCT p FROM Post p " +
            "JOIN p.postInterestTags it " +
            "LEFT JOIN p.postCategoryTags ct " +
            "WHERE p.isDeleted = false "+
            "AND p.region = :region " +
            "AND (:interestTag IS NULL OR it.interestTag = :interestTag) " +
            "AND (:categoryTag IS NULL OR ct.categoryTag = :categoryTag)")
    List<Post> findPostsByRegionAndInterestTagAndCategoryTag(@Param("region") String region,
                                                             @Param("interestTag") InterestTag interestTag,
                                                             @Param("categoryTag") CategoryTag categoryTag);

}