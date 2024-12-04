package com.reborn.server.domain.job.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JobPostDetailDto {

    private String jobTitle; // 직업공고 이름
    private String companyName; // 회사이름
    private String hmUrl; // 공고 사이트 url
    private String workAddr; // 위치
    private String jobId; // 직업공고 id
    private String age; // 지원 연령
    private String ageLim; // 지원 연령 제한
    private String detailCont; // 세부내용
    private String clerkphone; // 담당자 연락처
    private String status; // 접수 상태
    private String start; // 접수 시작 일자
    private String end; // 접수 마감 일자


}
