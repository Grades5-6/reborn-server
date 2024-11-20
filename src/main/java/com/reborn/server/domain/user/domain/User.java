package com.reborn.server.domain.user.domain;

import com.reborn.server.domain.auth.domain.oauth.OauthProvider;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "nickname")
    private String nickName;

    private String name;

    private String email;

    @Column(name = "o_auth_provider")
    private OauthProvider oauthProvider;

    @Column(name = "introduce")
    private String introduce;

    @Column(name = "profile_image")
    private String profileImg;

    // 온보딩 화면
    @Column(name="employment_status")
    private String employmentStatus;

    @Column(name="region")
    private String region;

    @Column(name="interested_field")
    private String interestedField;

    @Builder
    public User(String name, String email, OauthProvider oauthProvider, String introduce, String profileImg, String employmentStatus, String region, String interestedField) {
        this.name = name;
        this.email = email;
        this.oauthProvider = oauthProvider;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.employmentStatus = employmentStatus;
        this.region = region;
        this.interestedField = interestedField;
    }

    public static User of(String name, String email, OauthProvider oauthProvider, String profileImg) {
        return User.builder()
                .name(name)
                .email(email)
                .oauthProvider(oauthProvider)
                .profileImg(profileImg)
                .build();
    }

    public void updateOnboardingInfo(String employmentStatus, String region, String interestedField) {
        this.employmentStatus = employmentStatus;
        this.region = region;
        this.interestedField = interestedField;
    }
}
