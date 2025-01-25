package com.reborn.server.domain.community.api;

import com.reborn.server.domain.community.application.PostLikeService;
import com.reborn.server.domain.community.dto.request.PostLikeRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community/posts/{postId}/likes")
public class PostLikeApi {

    private final PostLikeService postLikeService;

    public PostLikeApi(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    @PostMapping
    public ResponseEntity<Long> addLike(@PathVariable Long postId, @RequestBody PostLikeRequest postLikeRequest) {
        try {
            Long updatedLikeCount = postLikeService.addLike(postId, postLikeRequest.getUserId());
            return ResponseEntity.ok(updatedLikeCount);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<Long> removeLike(@PathVariable Long postId, @RequestBody PostLikeRequest postLikeRequest) {
        try {
            Long updatedLikeCount = postLikeService.removeLike(postId, postLikeRequest.getUserId());
            return ResponseEntity.ok(updatedLikeCount);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
