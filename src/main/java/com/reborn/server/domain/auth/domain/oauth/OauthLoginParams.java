package com.reborn.server.domain.auth.domain.oauth;

import org.springframework.util.MultiValueMap;

public interface OauthLoginParams {
    OauthProvider oauthProvider();
    MultiValueMap<String, String> makeBody();
}
