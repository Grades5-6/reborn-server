package com.reborn.server.domain.post.dto.request;

import com.reborn.server.domain.post.domain.CategoryTag;
import com.reborn.server.domain.post.domain.InterestTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostRequest {
    private Long authorId; // 사용자 ID
    private String title;
    private String content;
    private String region;
    private String postImage;
    private List<InterestTag> interestTags;
    private List<CategoryTag> categoryTags;
}
