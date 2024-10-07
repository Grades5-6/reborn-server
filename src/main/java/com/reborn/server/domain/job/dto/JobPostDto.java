package com.reborn.server.domain.job.dto;

import com.reborn.server.domain.job.domain.JobPost;
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
    private String jobName; // 공고 제목
    private String companyName; // 회사 이름
    private String workLocation; // 위치
    private String status; // 공고 상태 (접수중/마감)
    private String start; // 접수 시작 일자
    private String end; // 접수 마감 일자

    public JobPostDto(String jobId, String jobName, String companyName, String workLocation, String status, String start, String end) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.companyName = companyName;
        this.workLocation = workLocation;
        this.status = status;
        this.start = start;
        this.end = end;
    }

    public static JobPostDto from(JobPost jobPost){
        return JobPostDto.builder()
                .jobId(jobPost.getJobId())
                .jobName(jobPost.getJobName())
                .companyName(jobPost.getCompanyName())
                .workLocation(jobPost.getWorkLocation())
                .status(jobPost.getStatus())
                .start(jobPost.getStart())
                .end(jobPost.getEnd())
                .build();
    }
}
