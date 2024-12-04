package com.reborn.server.domain.user.dto.request;

import com.reborn.server.domain.license.dto.response.LicenseResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLicensesRequest {
    private List<LicenseResponseDto> licenses;
}
