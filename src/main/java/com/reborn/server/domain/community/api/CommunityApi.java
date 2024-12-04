package com.reborn.server.domain.community.api;

import com.reborn.server.domain.community.application.CommunityService;
import com.reborn.server.domain.community.domain.CommunityPost;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class CommunityApi {
    private final CommunityService communityService;

    public CommunityApi(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/posts")
    public List<CommunityPost> getAllPosts() {
        return communityService.getAllPosts();
    }
}
