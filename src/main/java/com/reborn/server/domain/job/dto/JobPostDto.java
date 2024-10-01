package com.reborn.server.domain.job.dto;

import com.reborn.server.domain.job.domain.JobPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class JobPostDto {
    private Long jobPostId;
    private String title;
    private String content;
    private String company;
    private String location;
    private String deadline;


//    public JobPostDto(Long jobPostId, String title, String content, String company, String location, String deadline) {
//        this.jobPostId = jobPostId;
//        this.title = title;
//        this.content = content;
//        this.company = company;
//        this.location = location;
//        this.deadline = deadline;
//    }

    public static JobPostDto from(JobPost jobPost){
        return JobPostDto.builder()
                .jobPostId(jobPost.getId())
                .title(jobPost.getTitle())
                .content(jobPost.getContent())
                .company(jobPost.getCompany())
                .location(jobPost.getLocation())
                .deadline(jobPost.getDeadline())
                .build();
    }


}
