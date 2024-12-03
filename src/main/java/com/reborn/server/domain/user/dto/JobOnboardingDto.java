package com.reborn.server.domain.user.dto;

import com.reborn.server.domain.license.dto.request.LicenseRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class JobOnboardingDto {
    private String sex;
    private int year;
    private List<LicenseRequestDto> licenses;
}
