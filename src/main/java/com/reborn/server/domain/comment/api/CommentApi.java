package com.reborn.server.domain.comment.api;

import com.reborn.server.domain.comment.application.CommentService;
import com.reborn.server.domain.comment.dto.request.CommentModifyRequest;
import com.reborn.server.domain.comment.dto.request.CommentRequest;
import com.reborn.server.domain.comment.dto.response.CommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/comments")
public class CommentApi {

    private final CommentService commentService;

    // 댓글 조회
    @GetMapping("/{comment_id}")
    @Operation(summary = "댓글 조회")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long comment_id){
        try{
            CommentResponse commentResponse = commentService.getComment(comment_id);
            return new ResponseEntity<>(commentResponse, HttpStatus.OK);
        }catch(EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // 댓글 작성
    @PostMapping
    @Operation(summary = "댓글 작성")
    public ResponseEntity<CommentResponse> createComment (@RequestBody CommentRequest commentRequest){
        try{
            CommentResponse commentResponse =
                    commentService.createComment(commentRequest.getUserId(), commentRequest.getPostId(), commentRequest.getParentId(), commentRequest.getText());
        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 댓글 수정
    @PutMapping("/{comment_id}")
    @Operation(summary = "댓글 수정")
    public ResponseEntity<CommentResponse> modifyComment(@PathVariable Long comment_id, @RequestBody CommentModifyRequest commentModifyRequest){
        try{
            CommentResponse commentResponse =
                    commentService.modifyComment(comment_id, commentModifyRequest.getText());
            return new ResponseEntity<>(commentResponse, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{comment_id}")
    @Operation(summary = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable Long comment_id){
        commentService.deleteComment(comment_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
