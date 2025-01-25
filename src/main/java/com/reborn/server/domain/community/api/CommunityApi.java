package com.reborn.server.domain.community.api;

import com.reborn.server.domain.community.application.CommunityService;
import com.reborn.server.domain.community.domain.CommunityPost;
import com.reborn.server.domain.community.dto.request.CommunityPostRequest;
import com.reborn.server.domain.community.dto.request.CommunityPostUpdateRequest;
import com.reborn.server.domain.community.dto.response.CommunityPostResponse;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("/posts")
    @Operation(summary = "게시글 작성")
    public ResponseEntity<CommunityPostResponse> createPosts(@RequestBody CommunityPostRequest communityPostRequest) {
        try {
            CommunityPostResponse newPost = communityService.createPosts(communityPostRequest);
            return ResponseEntity.ok(newPost);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 조회")
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
    @Operation(summary = "게시글 수정")
    public ResponseEntity<CommunityPostResponse> updatePosts(@PathVariable Long postId, @RequestBody CommunityPostUpdateRequest communityPostUpdateRequest) {
        try {
            CommunityPostResponse updatedPost = communityService.updatePosts(postId, communityPostUpdateRequest);
            return ResponseEntity.ok(updatedPost);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/posts/{postId}")
    @Operation(summary = "게시글 삭제")
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