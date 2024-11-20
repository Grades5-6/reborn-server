package com.reborn.server.infra;

import com.reborn.server.domain.job.application.JobPostDetailService;
import com.reborn.server.domain.job.application.JobPostService;
import com.reborn.server.domain.job.dto.JobPostDetailDto;
import com.reborn.server.domain.job.dto.JobPostDto;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class ForecastApi {

    private final RestTemplate restTemplate;

    private final JobPostService jobPostService;
    private final JobPostDetailService jobPostDetailService;

    @Value("${openApi.service-key}")
    private String serviceKey;

    @Value("${openApi.base-url}")
    private String baseUrl;

    @Value("${openApi.detail-url}")
    private String detailUrl;


    @GetMapping
    public ResponseEntity<List<JobPostDto>> getJobPost() throws Exception {
        jobPostService.syncJobData(restTemplate, baseUrl, serviceKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<JobPostDetailDto> getJobPostDetail(@RequestParam String jobId) throws Exception {
        JobPostDetailDto jobPostDetail = jobPostDetailService.getJobPostDetail(restTemplate, detailUrl, serviceKey, jobId);
        return new ResponseEntity<>(jobPostDetail,HttpStatus.OK);
    }
}



