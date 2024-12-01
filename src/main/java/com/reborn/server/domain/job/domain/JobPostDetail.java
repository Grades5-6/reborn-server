package com.reborn.server.domain.job.domain;

import com.reborn.server.domain.job.dto.JobPostDetailDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "job")
public class JobPostDetail {

    // api 받아오면 수정할 예정
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Builder
    public JobPostDetail(String jobId, String age, String ageLim, String jobTitle, String detailCont, String clerkphone, String companyName, String hmUrl, String workAddr, String status, String start, String end) {
        this.jobId = jobId;
        this.age = age;
        this.ageLim = ageLim;
        this.jobTitle = jobTitle;
        this.detailCont = detailCont;
        this.clerkphone = clerkphone;
        this.companyName = companyName;
        this.hmUrl = hmUrl;
        this.workAddr = workAddr;
        this.status = status;
        this.start = start;
        this.end = end;
    }


}
