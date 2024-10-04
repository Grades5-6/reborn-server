package com.reborn.server.domain.job.domain;

import com.reborn.server.domain.job.dto.JobPostDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "job")
public class JobPost {

    // api 받아오면 수정할 예정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobId;
    private String jobName;
    private String companyName;
    private String workLocation;
    private String status;
    private String start;
    private String end;

    @Builder
    public JobPost(String jobId, String jobName, String companyName, String workLocation, String status, String start, String end) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.companyName = companyName;
        this.workLocation = workLocation;
        this.status = status;
        this.start = start;
        this.end = end;
    }

    @Builder
    public JobPost(JobPostDto jobPostDto) {
        this.jobId = jobPostDto.getJobId();
        this.jobName = jobPostDto.getJobName();
        this.companyName = jobPostDto.getCompanyName();
        this.workLocation = jobPostDto.getWorkLocation();
        this.status = jobPostDto.getStatus();
        this.start = jobPostDto.getStart();
        this.end = jobPostDto.getEnd();
    }


}
