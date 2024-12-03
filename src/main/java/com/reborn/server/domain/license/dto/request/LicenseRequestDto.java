package com.reborn.server.domain.license.dto.request;

import com.reborn.server.domain.license.domain.License;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LicenseRequestDto {
    private String jmfldnm;
    private String seriesnm;

    public License toEntity() {
        return License.builder()
                .jmfldnm(this.jmfldnm)
                .seriesnm(this.seriesnm)
                .build();
    }
}
