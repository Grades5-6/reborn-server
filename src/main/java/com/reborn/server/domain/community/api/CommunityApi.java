package com.reborn.server.domain.community.api;

import com.reborn.server.domain.community.application.CommunityService;
import com.reborn.server.domain.community.domain.CommunityPost;
import com.reborn.server.domain.community.dto.request.CommunityPostRequest;
import com.reborn.server.domain.community.dto.request.CommunityPostUpdateRequest;
import com.reborn.server.domain.community.dto.response.CommunityPostResponse;
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
    public ResponseEntity<Void> createPosts(@RequestBody CommunityPostRequest communityPostRequest) {
        try {
            communityService.createPosts(communityPostRequest);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<CommunityPostResponse> getPosts(@PathVariable Long postId) {
        try {
            CommunityPostResponse post = communityService.getPosts(postId);
            return ResponseEntity.ok(post);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<Long> updatePosts(@PathVariable Long postId, @RequestBody CommunityPostUpdateRequest communityPostUpdateRequest) {
        try {
            Long updatePostId = communityService.updatePosts(postId, communityPostUpdateRequest);
            return ResponseEntity.ok(updatePostId);
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
