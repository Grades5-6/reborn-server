package com.reborn.server.domain.license.application;

import com.reborn.server.domain.license.dao.LicenseRepository;
import com.reborn.server.domain.license.domain.License;
import com.reborn.server.domain.license.dto.response.LicenseResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseService {
    private final LicenseRepository licenseRepository;

    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }
    
    public List<LicenseResponseDto> getAllLicenses() {
        return licenseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public License findLicenseByJmfldnm(String jmfldnm) {
        return licenseRepository.findByJmfldnm(jmfldnm)
                .orElseThrow(() -> new IllegalArgumentException("License not found with jmfldnm: " + jmfldnm));
    }

    private LicenseResponseDto convertToDto(License license) {
        return LicenseResponseDto.builder()
                .jmfldnm(license.getJmfldnm())
                .seriesnm(license.getSeriesnm())
                .build();
    }
}
