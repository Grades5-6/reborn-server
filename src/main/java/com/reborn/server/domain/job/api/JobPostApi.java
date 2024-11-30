package com.reborn.server.domain.job.api;

import com.reborn.server.domain.job.application.JobPostDetailService;
import com.reborn.server.domain.job.application.JobPostSearchService;
import com.reborn.server.domain.job.application.JobPostService;
import com.reborn.server.domain.job.dto.JobPostDetailDto;
import com.reborn.server.domain.job.dto.JobPostSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/jobs/posts")
@RequiredArgsConstructor
public class JobPostApi {

    @Value("${openApi.detail-url}")
    private String detailUrl;

    @Value("${openApi.service-key}")
    private String serviceKey;

    private final RestTemplate restTemplate;

    private final JobPostService jobPostService;
    private final JobPostSearchService jobPostSearchService;
    private final JobPostDetailService jobPostDetailService;

    @GetMapping
    public ResponseEntity<List<JobPostDetailDto>> getAllJobPost() {
        List<JobPostDetailDto> jobPostDetail = jobPostService.getAllJobPosts();
        return ResponseEntity.ok(jobPostDetail);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobPostSearchDto>> searchJobPosts(@RequestParam String keyword) {
        List<JobPostSearchDto> searchResults = jobPostSearchService.searchJobPosts(keyword);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/detail")
    public ResponseEntity<JobPostDetailDto> getJobPostDetail(@RequestParam String jobId) throws Exception {
        JobPostDetailDto jobPostDetail = jobPostDetailService.getJobPostDetail(restTemplate, detailUrl, serviceKey, jobId);
        return new ResponseEntity<>(jobPostDetail, HttpStatus.OK);
    }
}
