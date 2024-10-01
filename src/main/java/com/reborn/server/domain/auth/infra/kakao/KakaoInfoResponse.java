package com.reborn.server.domain.auth.infra.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reborn.server.domain.auth.domain.oauth.OauthInfoResponse;
import com.reborn.server.domain.auth.domain.oauth.OauthProvider;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoInfoResponse implements OauthInfoResponse {

    private String email;

    private String name;

    private String picture;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public OauthProvider getOauthProvider() {
        return OauthProvider.KAKAO;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getProfileImageUrl() {
        return picture;
    }
}