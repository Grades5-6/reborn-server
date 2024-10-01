package com.reborn.server.domain.user.domain;

import com.reborn.server.domain.auth.domain.oauth.OauthProvider;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Builder
    public User(String name, String email, OauthProvider oauthProvider, String introduce, String profileImg) {
        this.name = name;
        this.email = email;
        this.oauthProvider = oauthProvider;
        this.introduce = introduce;
        this.profileImg = profileImg;
    }

    public static User of(String name, String email, OauthProvider oauthProvider, String profileImg) {
        return User.builder()
                .name(name)
                .email(email)
                .oauthProvider(oauthProvider)
                .profileImg(profileImg)
                .build();
    }
}
