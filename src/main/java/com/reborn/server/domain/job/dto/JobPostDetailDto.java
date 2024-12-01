package com.reborn.server.domain.job.dto;

import com.reborn.server.domain.job.domain.JobPostDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class JobPostDetailDto {

    private String jobId; // 직업공고 id
    private String age; // 지원 연령
    private String ageLim; // 지원 연령 제한
    private String jobTitle; // 직업공고 이름
    private String detailCont; // 세부내용
    private String clerkphone; // 담당자 연락처
    private String companyName; // 회사이름
    private String hmUrl; // 공고 사이트 url
    private String workAddr; // 위치
    private String status; // 접수 상태
    private String start; // 접수 시작 일자
    private String end; // 접수 마감 일자

    public JobPostDetailDto(String jobId, String age, String ageLim, String jobTitle, String start, String end, String detailCont, String clerkphone, String hmUrl, String companyName, String workAddr) {
        this.jobId = jobId;
        this.age = age;
        this.ageLim = ageLim;
        this.jobTitle = jobTitle;
        this.start = start;
        this.end = end;
        this.detailCont = detailCont;
        this.clerkphone = clerkphone;
        this.hmUrl = hmUrl;
        this.companyName = companyName;
        this.workAddr = workAddr;
    }

    public static JobPostDetailDto from(JobPostDetail jobPostDetail) {
        return JobPostDetailDto.builder()
                .jobId(jobPostDetail.getJobId())
                .age(jobPostDetail.getAge())
                .ageLim(jobPostDetail.getAgeLim())
                .jobTitle(jobPostDetail.getJobTitle())
                .detailCont(jobPostDetail.getDetailCont())
                .clerkphone(jobPostDetail.getClerkphone())
                .companyName(jobPostDetail.getCompanyName())
                .hmUrl(jobPostDetail.getHmUrl())
                .workAddr(jobPostDetail.getWorkAddr())
                .status(jobPostDetail.getStatus())
                .start(jobPostDetail.getStart())
                .end(jobPostDetail.getEnd())
                .build();
    }
}
