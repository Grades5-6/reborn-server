package com.reborn.server.domain.community.api;

import com.reborn.server.domain.community.application.CommunityService;
import com.reborn.server.domain.community.domain.CommunityPost;
import com.reborn.server.domain.community.dto.request.CommunityPostRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/posts")
    public ResponseEntity<CommunityPost> createPosts(@RequestBody CommunityPostRequest communityPostRequest) {
        try {
            CommunityPost newPost = communityService.createPosts(communityPostRequest);
            return ResponseEntity.ok(newPost);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<CommunityPost> getPosts(@PathVariable Long postId) {
        try {
            CommunityPost newPost = communityService.getPosts(postId);
            return ResponseEntity.ok(newPost);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Object> deletePosts(@PathVariable Long postId) {
        try {
            communityService.deletePosts(postId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
