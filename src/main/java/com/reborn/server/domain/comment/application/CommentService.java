package com.reborn.server.domain.comment.application;

import com.reborn.server.domain.comment.dao.CommentRepository;
import com.reborn.server.domain.comment.domain.Comment;
import com.reborn.server.domain.comment.dto.response.CommentResponse;
import com.reborn.server.domain.community.dao.CommunityPostRepository;
import com.reborn.server.domain.community.domain.CommunityPost;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
// user랑 post id 조회 service 계층에서 수행
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommunityPostRepository communityPostRepository;
    private final UserRepository userRepository;

    @Transactional
    // 댓글 작성
    public CommentResponse createComment(Long userId, Long postId, Long parentId, String text) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("no User id with" + userId));

        // 게시물 조회
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(()->new EntityNotFoundException("no Post id with" + postId));

        // 댓글 엔터티 생성 및 저장
        Comment comment = Comment.of(post, user, text, parentId);
        commentRepository.save(comment);

        // response dto에 담아서 클라이언트로 반환
        return CommentResponse.of(comment);

    }

    // 댓글 수정
    @Transactional
    public CommentResponse modifyComment(Long commentId, String text) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("no Comment id with "+commentId));

        comment.setText(text);
        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("no Comment id with "+commentId));
        }


    }






