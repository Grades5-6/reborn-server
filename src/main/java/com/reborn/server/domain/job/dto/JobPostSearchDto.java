package com.reborn.server.domain.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostSearchDto {
    private String jobId;      // 직업공고 ID
    private String jobTitle;   // 직업공고 이름
    private String companyName; // 회사 이름
    private String workAddr;    // 위치
    private String hmUrl;      // 공고 사이트 URL
}
