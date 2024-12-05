package com.reborn.server.domain.job.api;

import com.reborn.server.domain.job.application.JobPostSearchService;
import com.reborn.server.domain.job.application.JobPostService;
import com.reborn.server.domain.job.dto.response.JobResponseDto;
import com.reborn.server.domain.license.dto.response.LicenseResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @GetMapping
    public ResponseEntity<List<JobResponseDto>> getAllJobPost() {
        List<JobResponseDto> jobPostDetail = jobPostService.getAllJobDetails();
        return ResponseEntity.ok(jobPostDetail);
    }

    @GetMapping("/search")
    public ResponseEntity<List<JobResponseDto>> searchJobPosts(@RequestParam String keyword) {
        List<JobResponseDto> searchResults = jobPostSearchService.searchJobPosts(keyword);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/licenses")
    public ResponseEntity<List<JobResponseDto>> getJobPostByLicenses(@RequestParam String jmfldnm) {
        List<JobResponseDto> searchResults = jobPostSearchService.getJobPostsByLicenses(jmfldnm);
        return ResponseEntity.ok(searchResults);
    }
}
