package com.reborn.server.domain.auth.infra.naver;

import com.reborn.server.domain.auth.domain.oauth.OauthLoginParams;
import com.reborn.server.domain.auth.domain.oauth.OauthProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 네이버 api 요청에 필요한 accessToken & state 값을 가지고 있는 클래스
// code & state 값 필요
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NaverLoginParams implements OauthLoginParams {
    private String accessToken;

    @Override
    public OauthProvider oauthProvider(){
        return OauthProvider.NAVER;
    }
}
