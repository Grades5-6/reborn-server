package com.reborn.server.domain.community.domain;

import lombok.Getter;

@Getter
public enum InterestTag {
    STORE_MANAGEMENT("매장관리"),
    EDUCATION("교육"),
    COUNSELING("상담"),
    AGRICULTURE("농업"),
    IT("IT"),
    PARCEL_SERVICE("택배"),
    SERVICE("서비스"),
    MEDIA("미디어"),
    WHITE_COLLAR("사무직");

    private final String name;

    InterestTag(String name) {
        this.name = name;
    }

}