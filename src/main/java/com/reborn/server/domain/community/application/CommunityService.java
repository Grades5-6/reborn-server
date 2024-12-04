package com.reborn.server.domain.community.application;

import com.reborn.server.domain.community.dao.CommunityPostRepository;
import com.reborn.server.domain.community.domain.CommunityPost;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {
    private final CommunityPostRepository communityPostRepository;

    public CommunityService(CommunityPostRepository communityPostRepository) {
        this.communityPostRepository = communityPostRepository;
    }

    public List<CommunityPost> getAllPosts() {
        return communityPostRepository.findAll();
    }
}
