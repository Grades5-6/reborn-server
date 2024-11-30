package com.reborn.server.domain.license.dto.response;

import com.fasterxml.jackson.core.JsonToken;
import com.reborn.server.domain.license.domain.License;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LicenseResponseDto {
    private String jmcd;
    private String jmfldnm;
    private String mdobligfldnm;
    private String obligfldnm;
    private String qualgbnm;
    private String seriesnm;

    public License toEntity() {
        return License.builder()
                .jmcd(this.jmcd)
                .jmfldnm(this.jmfldnm)
                .mdobligfldnm(this.mdobligfldnm)
                .obligfldnm(this.obligfldnm)
                .qualgbnm(this.qualgbnm)
                .seriesnm(this.seriesnm)
                .build();
    }
}
