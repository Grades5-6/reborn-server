package com.reborn.server.domain.community.domain;

import com.reborn.server.domain.comment.domain.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String author;
    private String title;
    private String content;

    private int likes_count = 0;
    // private int comments_count = 0;

    // 하나의 포스트에 여러 개의 댓글
    // 포스트 로드 될 때 관련 댓글도 로드됨 & 포스트 삭제 시 관련 댓글도 삭제
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc") // 댓글 정렬
    private List<Comment> comments;
}
