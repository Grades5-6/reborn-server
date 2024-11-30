package com.reborn.server.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMyPageResponse {
    private String nickName;
    private String profileImg;
    private String rebornTemperature;
    private String employmentStatus;
    private String region;
    private List<String> interestedField;
}
