package com.reborn.server.domain.community.dto.response;

import com.reborn.server.domain.community.domain.CategoryTag;
import com.reborn.server.domain.community.domain.InterestTag;
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
    private int likesCount;
    private int commentsCount;
    private LocalDateTime createdAt;
    private List<String> interestTags;
    private List<String> categoryTag;
}
