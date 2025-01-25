package com.reborn.server.domain.community.dto.response;

import com.reborn.server.domain.community.domain.CommunityPost;
import com.reborn.server.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommunityPostResponse {

    //author
    private Long authorId;
    private String authorNickName;
    private String authorProfileImg;
    private String authorRegion;
    private List<String> authorInterestTag;
    private String authorEmploymentStatus;
    private Integer authorRebornTemperature;

    //post
    private String title;
    private String content;
    private String region;
    private String postImage;
    private Long likesCount;
    private int commentsCount;
    private LocalDateTime createdAt;
    private List<String> interestTags;
    private List<String> categoryTags;

    public static CommunityPostResponse from(User author, CommunityPost post, List<String> interestTags, List<String> categoryTags) {
        return CommunityPostResponse.builder()
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
                .commentsCount(post.getCommentsCount())
                .createdAt(post.getCreatedAt())
                .interestTags(interestTags)
                .categoryTags(categoryTags)
                .build();
    }
}
