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

    private LicenseResponseDto convertToDto(License license) {
        return LicenseResponseDto.builder()
                .jmcd(license.getJmcd())
                .jmfldnm(license.getJmfldnm())
                .mdobligfldnm(license.getMdobligfldnm())
                .obligfldnm(license.getObligfldnm())
                .qualgbnm(license.getQualgbnm())
                .seriesnm(license.getSeriesnm())
                .build();
    }
}
