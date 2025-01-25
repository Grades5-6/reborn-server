package com.reborn.server.domain.post.dao;

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
}