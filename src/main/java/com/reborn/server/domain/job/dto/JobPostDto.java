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

    private String jobId;
    private String jobName;
    private String companyName;
    private String workLocation;
    private String deadline;
    private String start;

    public JobPostDto(String jobId, String jobName, String companyName, String workLocation, String deadline, String start) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.companyName = companyName;
        this.workLocation = workLocation;
        this.deadline = deadline;
        this.start = start;
    }

    public static JobPostDto from(JobPost jobPost){
        return JobPostDto.builder()
                .jobId(jobPost.getJobId())
                .jobName(jobPost.getJobName())
                .companyName(jobPost.getCompanyName())
                .workLocation(jobPost.getWorkLocation())
                .deadline(jobPost.getDeadline())
                .start(jobPost.getStart())
                .build();
    }
}
