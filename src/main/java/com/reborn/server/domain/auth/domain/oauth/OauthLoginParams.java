package com.reborn.server.domain.auth.domain.oauth;

public interface OauthLoginParams {
    OauthProvider oauthProvider();
    String getAccessToken();
}
