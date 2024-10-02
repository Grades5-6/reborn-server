package com.reborn.server.domain.job.domain;

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
    private String deadline;
    private String start;

    @Builder
    public JobPost(String jobId, String jobName, String companyName, String workLocation, String deadline, String start) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.companyName = companyName;
        this.workLocation = workLocation;
        this.deadline = deadline;
        this.start = start;
    }
}
