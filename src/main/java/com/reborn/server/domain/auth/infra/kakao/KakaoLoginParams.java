package com.reborn.server.domain.auth.infra.kakao;

import com.reborn.server.domain.auth.domain.oauth.OauthLoginParams;
import com.reborn.server.domain.auth.domain.oauth.OauthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoLoginParams implements OauthLoginParams {
    private String authorizationCode;

    @Override
    public OauthProvider oauthProvider() {
        return OauthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorizationCode);
        return body;
    }
}