package com.reborn.server.domain.community.domain;

import lombok.Getter;

@Getter
public enum CategoryTag {
    INFORMATION("정보공유"),
    ANNOUNCEMENT("공고"),
    COUNSELING("고민상담"),
    CLUB("소모임"),
    REVIEW("후기");

    private final String name;

    CategoryTag(String name) {
        this.name = name;
    }

}
