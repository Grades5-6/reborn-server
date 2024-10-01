package com.reborn.server.domain.auth.domain.oauth;

public interface OauthInfoResponse {
    String getEmail();
    String getName();
    OauthProvider getOauthProvider();
    String getProfileImageUrl();
}
