package com.reborn.server.domain.license.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reborn.server.domain.license.domain.License;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  LicenseResponseDto {
    private String jmfldnm;
    private String seriesnm;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    public License toEntity() {
        return License.builder()
                .jmfldnm(this.jmfldnm)
                .seriesnm(this.seriesnm)
                .expirationDate(this.expirationDate)
                .build();
    }

    @Builder
    public LicenseResponseDto(License license) {
        this.jmfldnm = license.getJmfldnm();
        this.seriesnm = license.getSeriesnm();
        this.expirationDate = license.getExpirationDate();
    }
}
