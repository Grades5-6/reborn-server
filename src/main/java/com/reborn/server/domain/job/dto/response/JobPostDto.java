package com.reborn.server.domain.job.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

// 내가 필요한 데이터 DB에 저장
@Builder
@Getter
@AllArgsConstructor
public class JobPostDto {
    private Long jobPostId;

    private String jobId; // 직업공고 id
    private String jobTitle; // 공고 제목
    private String companyName; // 회사 이름
    private String workLocation; // 위치
    private String status; // 공고 상태 (접수중/마감)
    private String start; // 접수 시작 일자
    private String end; // 접수 마감 일자

    public JobPostDto(String jobId, String jobTitle, String companyName, String workLocation, String status, String start, String end) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.workLocation = workLocation;
        this.status = status;
        this.start = start;
        this.end = end;
    }

}
