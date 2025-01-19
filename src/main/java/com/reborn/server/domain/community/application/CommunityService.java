package com.reborn.server.domain.community.application;

import com.reborn.server.domain.community.dao.CommunityPostRepository;
import com.reborn.server.domain.community.dao.HashTagRepository;
import com.reborn.server.domain.community.dao.PostHashTagRepository;
import com.reborn.server.domain.community.domain.CommunityPost;
import com.reborn.server.domain.community.dto.request.CommunityPostRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommunityService {
    private final CommunityPostRepository communityPostRepository;
    private final HashTagRepository hashTagRepository;
    private final PostHashTagRepository postHashTagRepository;

    public CommunityService(CommunityPostRepository communityPostRepository,
                            HashTagRepository hashTagRepository,
                            PostHashTagRepository postHashTagRepository) {
        this.communityPostRepository = communityPostRepository;
        this.hashTagRepository = hashTagRepository;
        this.postHashTagRepository = postHashTagRepository;
    }

    @Transactional
    public List<CommunityPost> getAllPosts() {
        return communityPostRepository.findAll();
    }

    @Transactional
    public CommunityPost createPosts(CommunityPostRequest communityPostRequest) {
        // 게시글 생성
        CommunityPost post = CommunityPost.builder()
                .author(communityPostRequest.getAuthor())
                .title(communityPostRequest.getTitle())
                .content(communityPostRequest.getContent())
                .region(communityPostRequest.getRegion())
                .postImage(communityPostRequest.getPostImage())
                .build();

        CommunityPost savedPost = communityPostRepository.save(post);

        return null;
    }

    @Transactional
    public CommunityPost getPosts(Long postId) {
        return null;
    }

    @Transactional
    public void deletePosts(Long postId) {

    }
}
