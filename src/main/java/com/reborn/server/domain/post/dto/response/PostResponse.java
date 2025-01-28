package com.reborn.server.domain.post.dto.response;

import com.reborn.server.domain.post.domain.Post;
import com.reborn.server.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse {

    //author
    private Long authorId;
    private String authorNickName;
    private String authorProfileImg;
    private String authorRegion;
    private List<String> authorInterestTag;
    private String authorEmploymentStatus;
    private Integer authorRebornTemperature;

    //post
    private Long id;
    private String title;
    private String content;
    private String region;
    private String postImage;
    private Long likesCount;
    private int commentsCount;
    private LocalDateTime createdAt;
    private List<String> interestTags;
    private List<String> categoryTags;

    private int commentCounts;

    public static PostResponse from(User author, Post post, List<String> interestTags, List<String> categoryTags, int commentCounts) {
        return PostResponse.builder()
                .authorId(author.getId())
                .authorNickName(author.getNickName())
                .authorProfileImg(author.getProfileImg())
                .authorRegion(author.getRegion())
                .authorInterestTag(author.getInterestedField())
                .authorEmploymentStatus(author.getEmploymentStatus())
                .authorRebornTemperature(author.getRebornTemperature())
                .title(post.getTitle())
                .content(post.getContent())
                .region(post.getRegion())
                .postImage(post.getPostImage())
                .likesCount(post.getLikesCount())
                .commentCounts(commentCounts)
                .createdAt(post.getCreatedAt())
                .interestTags(interestTags)
                .categoryTags(categoryTags)
                .commentCounts(post.getCommentsCount())
                .build();
    }

    public static PostResponse of(Post post, int commentsCount) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorNickName(post.getAuthor().getNickName())
                .region(post.getRegion())
                .likesCount(post.getLikesCount())
                .commentsCount(commentsCount)
                .build();
    }
}
