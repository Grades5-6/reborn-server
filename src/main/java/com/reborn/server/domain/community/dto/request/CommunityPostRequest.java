package com.reborn.server.domain.community.dto.request;

import com.reborn.server.domain.community.domain.CategoryTag;
import com.reborn.server.domain.community.domain.InterestTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommunityPostRequest {
    private Long authorId; // 사용자 ID
    private String title;
    private String content;
    private String region;
    private String postImage;
    private List<InterestTag> interestTags;
    private List<CategoryTag> categoryTags;
}
