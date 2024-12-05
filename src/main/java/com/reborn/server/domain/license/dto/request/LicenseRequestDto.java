package com.reborn.server.domain.license.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class LicenseRequestDto {
    private String jmfldnm;
    private String seriesnm;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
}
