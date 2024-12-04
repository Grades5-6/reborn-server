package com.reborn.server.domain.job.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JobResponseDto {
    private String jobTitle; // 직업공고 이름
    private String companyName; // 회사이름
    private String hmUrl; // 공고 사이트 url
    private String workAddr; // 위치
}
