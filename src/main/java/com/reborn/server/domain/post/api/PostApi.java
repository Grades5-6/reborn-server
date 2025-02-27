package com.reborn.server.domain.post.api;

import com.reborn.server.domain.post.application.PostService;
import com.reborn.server.domain.post.domain.CategoryTag;
import com.reborn.server.domain.post.domain.InterestTag;
import com.reborn.server.domain.post.dto.request.PostRequest;
import com.reborn.server.domain.post.dto.request.PostUpdateRequest;
import com.reborn.server.domain.post.dto.response.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/community")
public class PostApi {
    private final PostService postService;

    public PostApi(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    @Operation(summary = "게시글 작성")
    public ResponseEntity<PostResponse> createPosts(@RequestBody PostRequest postRequest) {
        try {
            PostResponse newPost = postService.createPosts(postRequest);
            return ResponseEntity.ok(newPost);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/posts/{postId}")
    @Operation(summary = "게시글 조회")
    public ResponseEntity<PostResponse> getPosts(@PathVariable Long postId) {
        try {
            PostResponse post = postService.getPosts(postId);
            return ResponseEntity.ok(post);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/posts/{postId}")
    @Operation(summary = "게시글 수정")
    public ResponseEntity<PostResponse> updatePosts(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        try {
            PostResponse updatedPost = postService.updatePosts(postId, postUpdateRequest);
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
            postService.deletePosts(postId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/posts")
    @Operation(summary = "전체 게시물 조회")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
        try{
            List<PostResponse> posts = postService.getAllPosts();
            return ResponseEntity.ok(posts);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/posts/hashtag")
    @Operation(summary = "특정 해시태그를 가지는 게시물 조회")
    public ResponseEntity<List<PostResponse>> getPostsByHashtag(
            @RequestParam(value = "interestTag", required = false) InterestTag interestTag,
            @RequestParam(value = "categoryTag", required = false) CategoryTag categoryTag
    ){
        try{
            List<PostResponse> posts = postService.getPostsByHashtag(interestTag, categoryTag);
            return ResponseEntity.ok(posts);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/posts/region")
    @Operation(summary = "특정 지역과 특정 해시태그를 가지는 게시물 조회")
    public ResponseEntity<List<PostResponse>> getPostsByRegionAndHashtag(
            @RequestParam(value = "region") String region,
            @RequestParam(value = "interestTag", required = false) InterestTag interestTag,
            @RequestParam(value = "categoryTag", required = false) CategoryTag categoryTag
    ){
        try{
            List<PostResponse> posts = postService.getPostsByRegionAndHashtag(region, interestTag, categoryTag);
            return ResponseEntity.ok(posts);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}