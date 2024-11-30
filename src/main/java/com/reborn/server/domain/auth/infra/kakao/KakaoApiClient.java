package com.reborn.server.domain.auth.infra.kakao;

import com.reborn.server.domain.auth.domain.oauth.OauthApiClient;
import com.reborn.server.domain.auth.domain.oauth.OauthInfoResponse;
import com.reborn.server.domain.auth.domain.oauth.OauthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoApiClient implements OauthApiClient {

    @Value("${oauth.kakao.url.api}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    @Override
    public OauthProvider oauthProvider() {
        return OauthProvider.KAKAO;
    }

    @Override
    public OauthInfoResponse requestOauthInfo(String accessToken) {
        String url = apiUrl + "/user/me";
        System.out.println(accessToken);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
    }
}