package com.reborn.server.domain.auth.infra.naver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.reborn.server.domain.auth.domain.oauth.OauthInfoResponse;
import com.reborn.server.domain.auth.domain.oauth.OauthProvider;
import lombok.Getter;

// 요청한 결과값 중 필요한 값만 얻어오기
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverInfoResponse implements OauthInfoResponse {
//    private String email;
//    private String name;
//    private String picture;
//
//    @Override
//    public String getName() {
//        return name;
//    }
//
//    @Override
//    public OauthProvider getOauthProvider() {
//        return OauthProvider.NAVER;
//    }
//
//    @Override
//    public String getEmail() {
//        return email;
//    }
//
//    @Override
//    public String getProfileImageUrl() {
//        return picture;
//    }
private Response response;

    @Override
    public String getEmail() {
        return response.getEmail();
    }

    @Override
    public String getName() {
        return response.getName();
    }

    @Override
    public OauthProvider getOauthProvider() {
        return OauthProvider.NAVER;
    }

    @Override
    public String getProfileImageUrl() {
        return response.getProfileImage();
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        private String id;
        private String email;
        private String name;

        @JsonProperty("profile_image")
        private String profileImage;
    }
}
