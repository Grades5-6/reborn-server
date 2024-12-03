package com.reborn.server.domain.job.dto;

import com.reborn.server.domain.job.domain.JobPostDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JobPostDetailDto {

    private String jobId; // 직업공고 id
    private String jobTitle; // 직업공고 이름
    private String companyName; // 회사이름
    private String hmUrl; // 공고 사이트 url
    private String workAddr; // 위치


    public JobPostDetailDto(String jobId,String jobTitle, String companyName, String hmUrl, String workAddr){
        this.jobId=jobId;
        this.jobTitle=jobTitle;
        this.companyName=companyName;
        this.hmUrl=hmUrl;
        this.workAddr=workAddr;
    }


    public static JobPostDetailDto from(JobPostDetail jobPostDetail) {
        return JobPostDetailDto.builder()
                .jobId(jobPostDetail.getJobId())
                .jobTitle(jobPostDetail.getJobTitle())
                .companyName(jobPostDetail.getCompanyName())
                .hmUrl(jobPostDetail.getHmUrl())
                .workAddr(jobPostDetail.getWorkAddr())
                .build();
    }
}
