package com.reborn.server.domain.auth.infra.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Access Token 을 받아오기 위한 Response Model
@NoArgsConstructor // 기본 생성자 생성 어노테이션
@Getter
public class NaverTokens {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expiresIn;
}
