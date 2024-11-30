package com.reborn.server.domain.license.api;

import com.reborn.server.domain.license.application.LicenseService;
import com.reborn.server.domain.license.dto.response.LicenseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/licenses")
public class LicenseApi {
    private final LicenseService licenseService;

    @GetMapping
    public List<LicenseResponseDto> getAllLicenses() {
        return licenseService.getAllLicenses();
    }
}
