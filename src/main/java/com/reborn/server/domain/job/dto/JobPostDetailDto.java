package com.reborn.server.domain.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class JobPostDetailDto {
    // 필요한 정보 프론트로 전송
    private String jobId;
    private String age;
    private String ageLim;
    private String jobTitle;
    private String detailCont;
    private String clerkphone;
    private String companyName;
    private String hmUrl;
    private String workAddr;
    private String status;
    private String start;
    private String end;
    private String wantedNum;

    public JobPostDetailDto(String jobId, String age, String ageLim, String jobTitle, String wantedNum,String start, String end, String detailCont, String clerkphone, String hmUrl, String companyName, String  workAddr) {
        this.jobId = jobId;
        this.age=age;
        this.ageLim=ageLim;
        this.jobTitle = jobTitle;
        this.wantedNum = wantedNum;
        this.start = start;
        this.end=end;
        this.detailCont=detailCont;
        this.clerkphone=clerkphone;
        this.hmUrl=hmUrl;
        this.companyName=companyName;
        this.workAddr = workAddr;
    }
}
