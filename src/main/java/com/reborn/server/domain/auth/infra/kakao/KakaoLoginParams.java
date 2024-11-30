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
    private String accessToken;

    @Override
    public OauthProvider oauthProvider() {
        return OauthProvider.KAKAO;
    }
}