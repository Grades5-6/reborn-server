package com.reborn.server.domain.community.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class CommunityPostRequest {
    private String author;
    private String title;
    private String content;
    private String region;
    private String postImage;
    private List<String> tags; // 해시태그 목록
}
