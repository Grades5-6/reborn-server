package com.reborn.server.domain.post.application;

import com.reborn.server.domain.post.dao.PostRepository;
import com.reborn.server.domain.post.dao.PostLikeRepository;
import com.reborn.server.domain.post.domain.Post;
import com.reborn.server.domain.post.domain.PostLike;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostLikeService(PostLikeRepository postLikeRepository,
                           PostRepository postRepository,
                           UserRepository userRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;

    }

    @Transactional
    public Long addLike(Long postId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        if (!postLikeRepository.existsByUserAndPost(user, post)) {
            PostLike postLike = PostLike.of(user, post);
            postLikeRepository.save(postLike);

            post.updateLikesCount(postLikeRepository.countAllByPost(post));
            Post updateLikPost = postRepository.save(post);

            return updateLikPost.getLikesCount();
        }else{
            throw new RuntimeException("Post Like Already Exists");
        }
    }

    @Transactional
    public Long removeLike(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            postLikeRepository.deleteByUserAndPost(user, post);

            post.updateLikesCount(postLikeRepository.countAllByPost(post));
            Post updateLikPost = postRepository.save(post);

            return updateLikPost.getLikesCount();
        }else{
            throw new RuntimeException("Post Like Not Exists");
        }
    }
}
