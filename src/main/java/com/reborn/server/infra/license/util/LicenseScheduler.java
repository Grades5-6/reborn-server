package com.reborn.server.infra.license.util;

import com.reborn.server.domain.license.dao.LicenseRepository;
import com.reborn.server.domain.license.domain.License;
import com.reborn.server.domain.license.dto.response.LicenseResponseDto;
import com.reborn.server.infra.license.api.LicenseApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LicenseScheduler {
    private final LicenseApiClient licenseApiClient;
    private final LicenseRepository licenseRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchLicenseData() {
        List<LicenseResponseDto> licenseResponseDtos = licenseApiClient.fetchLicenses();

        List<License> licenses = licenseResponseDtos.stream()
                .map(LicenseResponseDto::toEntity)
                .collect(Collectors.toList());
        licenseRepository.saveAll(licenses);
        System.out.println("자격증 정보가 성공적으로 업데이트 되었습니다.");
    }
}
