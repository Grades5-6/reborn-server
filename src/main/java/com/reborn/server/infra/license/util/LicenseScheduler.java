package com.reborn.server.infra.license.util;

import com.reborn.server.domain.license.dao.LicenseRepository;
import com.reborn.server.domain.license.domain.License;
import com.reborn.server.domain.license.dto.response.LicenseResponseDto;
import com.reborn.server.infra.license.api.LicenseApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LicenseScheduler {
    private final LicenseApiClient licenseApiClient;
    private final LicenseRepository licenseRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void fetchLicenseData() {
        List<LicenseResponseDto> licenseResponseDtos = licenseApiClient.fetchLicenses();

        Set<String> existingJmcds = licenseRepository.findAllJmcds();

        List<License> licenses = licenseResponseDtos.stream()
                .map(LicenseResponseDto::toEntity)
                .filter(license -> !existingJmcds.contains(license.getJmcd())) // 중복 제거
                .collect(Collectors.toList());
        licenseRepository.saveAll(licenses);
    }
}
