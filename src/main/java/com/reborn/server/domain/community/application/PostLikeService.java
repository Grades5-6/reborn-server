package com.reborn.server.domain.community.application;

import com.reborn.server.domain.community.dao.CommunityPostRepository;
import com.reborn.server.domain.community.dao.PostLikeRepository;
import com.reborn.server.domain.community.domain.CommunityPost;
import com.reborn.server.domain.community.domain.PostLike;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final CommunityPostRepository communityPostRepository;
    private final UserRepository userRepository;

    public PostLikeService(PostLikeRepository postLikeRepository,
                           CommunityPostRepository communityPostRepository,
                           UserRepository userRepository) {
        this.postLikeRepository = postLikeRepository;
        this.communityPostRepository = communityPostRepository;
        this.userRepository = userRepository;

    }

    @Transactional
    public Long addLike(Long postId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        if (!postLikeRepository.existsByUserAndPost(user, post)) {
            PostLike postLike = PostLike.of(user, post);
            postLikeRepository.save(postLike);

            post.updateLikesCount(postLikeRepository.countAllByPost(post));
            CommunityPost updateLikPost = communityPostRepository.save(post);

            return updateLikPost.getLikesCount();
        }else{
            throw new RuntimeException("Post Like Already Exists");
        }
    }

    @Transactional
    public Long removeLike(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post Not Found"));

        if (postLikeRepository.existsByUserAndPost(user, post)) {
            postLikeRepository.deleteByUserAndPost(user, post);

            post.updateLikesCount(postLikeRepository.countAllByPost(post));
            CommunityPost updateLikPost = communityPostRepository.save(post);

            return updateLikPost.getLikesCount();
        }else{
            throw new RuntimeException("Post Like Not Exists");
        }
    }
}
