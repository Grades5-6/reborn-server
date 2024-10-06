package com.reborn.server.domain.auth.domain.oauth;

public interface OauthApiClient {
    OauthProvider oauthProvider();
    OauthInfoResponse requestOauthInfo(String accessToken);
}
