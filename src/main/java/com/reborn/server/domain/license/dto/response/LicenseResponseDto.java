package com.reborn.server.domain.license.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LicenseResponseDto {
    private Long id;
    private String jmcd;
    private String jmfldnm;
    private String mdobligfldnm;
    private String obligfldnm;
    private String qualgbnm;
    private String seriesnm;
}
