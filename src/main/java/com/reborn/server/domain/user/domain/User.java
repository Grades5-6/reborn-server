package com.reborn.server.domain.user.domain;

import com.reborn.server.domain.auth.domain.oauth.OauthProvider;
import com.reborn.server.domain.license.domain.License;
import com.reborn.server.domain.license.dto.response.LicenseResponseDto;
import com.reborn.server.domain.user.dto.request.UserLicensesRequest;
import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
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

    @Column(name = "reborn_temperature")
    private Integer rebornTemperature; // 리본 온도

    @Column(name = "employment_status")
    private String employmentStatus; // "재직" or "퇴직"

    @Column(name = "region")
    private String region; // 동네

    @ElementCollection
    @Column(name="interested_field")
    private List<String> interestedField; // 관심 분야

    // 일자리 온보딩 화면
    @Column(name="sex")
    private String sex; // 성별

    @Column(name = "year")
    private Integer year; // 태어난 연도

    // To Do: fix ManyToMany
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<License> licenses = new ArrayList<>();

    public static User of(String name, String email, OauthProvider oauthProvider, String profileImg) {
        return User.builder()
                .name(name)
                .email(email)
                .oauthProvider(oauthProvider)
                .profileImg(profileImg)
                .build();
    }

    public void updateUserProfile(String nickName, String profileImg, String employmentStatus) {
        this.nickName = nickName;
        this.profileImg = profileImg;
        this.employmentStatus = employmentStatus;
    }

    public void updateUserInterests(List<String> interestedField) {
        this.interestedField = interestedField;
    }

    public void updateUserRegion(String region) {
        this.region = region;
    }


    public void updateOnboardingInfo(String employmentStatus, String region, List<String> interestedField) {
        this.employmentStatus = employmentStatus;
        this.region = region;
        this.interestedField = interestedField;
    }

    public void updateJobOnboardingData(String sex, int year, List<License> licenses) {
        this.sex= sex;
        this.year=year;
        this.licenses.clear();
        this.licenses.addAll(licenses);
    }

    public void updateUserLicenses(List<License> licenses) {
        this.licenses.clear();
        this.licenses.addAll(licenses);
    }
}
