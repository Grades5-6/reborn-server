package com.reborn.server.domain.comment.dao;

import com.reborn.server.domain.comment.domain.Comment;
import com.reborn.server.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    long countByPost(Post post);
}
