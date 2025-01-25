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
    public CommentResponse getComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new EntityNotFoundException("no Comment id with " + commentId));

        return CommentResponse.of(comment);
    }

    @Transactional
    // 댓글 작성
    public CommentResponse createComment(Long userId, Long postId, Long parentId, String text) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("no User id with" + userId));

        // 게시물 조회
        CommunityPost post = communityPostRepository.findById(postId)
                .orElseThrow(()->new EntityNotFoundException("no Post id with" + postId));

        // 부모 댓글 조회 (parentId가 null이 아닐 경우에만)
        Comment parent = null;
        if (parentId != null) {
            parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new EntityNotFoundException("no Parent Comment id with " + parentId));
        }

        // orderNum 계산
        long groupNum;
        if (parent == null) { // 부모 댓글
            groupNum = commentRepository.countByPost(post); // 현재 게시물에 달린 댓글 수
        } else { // 자식 댓글
            groupNum = parent.getGroupNum(); // 부모 댓글의 orderNum과 동일
        }

        // 댓글 엔터티 생성 및 저장
        Comment comment = Comment.of(post, user, text, parent, groupNum);

        // 부모 댓글에 자식 댓글 추가 (대댓글일 경우)
        if (parent != null) {
            parent.getChildrenComment().add(comment);
        }

        commentRepository.save(comment);

        // response dto에 담아서 클라이언트로 반환
        return CommentResponse.of(comment);

    }

    // 댓글 수정
    @Transactional
    public CommentResponse modifyComment(Long commentId, String text) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("no Comment id with "+commentId));

        comment.setText(text); // 내용 수정 및 수정된 시간 반영
        commentRepository.save(comment);

        return CommentResponse.of(comment);
    }

    // 댓글 삭제 -> soft deleted

    // 부모 댓글인데 자식 댓글이 없으면 -> 삭제
    // 부모 댓글인데, 자식 댓글이 있다면 -> 부모 댓글을 "삭제된 댓글입니다." & isDeleted = true 로 변경
    // 자식 댓글인데, 또 다른 자식 댓글이 있다면 -> 해당 댓글을 "삭제된 댓글입니다." & isDeleted = true 로 변경
    // 자식 댓글인데 자식 댓글이 없으면 -> 삭제
    // 만약에 부모 댓글인데 다른 자식 댓글이 다 isDeleted = true 이면 해당 그룹 댓글 전체 삭제

    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("no Comment id with " + commentId));

        if (comment.getParent() == null) { // 해당 댓글이 부모댓글인 경우
            comment.softDelete(true);
            commentRepository.save(comment);

            // 부모 댓글이 삭제된 상태에서 모든 자식 댓글이 삭제 상태 확인
            if (comment.areAllChildrenDeleted()) {
                deleteGroupCommentsIfParentDeleted(comment);
            }

        } else { // 해당 댓글이 자식 댓글인 경우
            comment.softDelete(true);
            commentRepository.save(comment);

            // 자식 댓글 삭제 && 부모 댓글의 모든 자식 댓글이 삭제 상태인지 확인
            Comment parent = comment.getParent();
            if (parent.areAllChildrenDeleted() && parent.isDeleted()) {
                deleteGroupCommentsIfParentDeleted(parent);
            }
        }
        commentRepository.save(comment);
    }

    private void deleteGroupCommentsIfParentDeleted(Comment parent) {
        parent.getChildrenComment().forEach(child -> {
            child.softDelete(true); // 모든 자식 댓글도 삭제 처리
            commentRepository.save(child);
        });
        parent.softDelete(true); // 부모 댓글 삭제
        commentRepository.save(parent);
    }
}






