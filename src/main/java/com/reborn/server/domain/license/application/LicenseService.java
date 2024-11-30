package com.reborn.server.domain.license.application;

import com.reborn.server.domain.license.dto.response.LicenseResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LicenseService {
    public List<LicenseResponseDto> getAllLicenses() {
        return new ArrayList<>();
    }
}
