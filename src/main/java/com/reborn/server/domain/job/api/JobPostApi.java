package com.reborn.server.domain.job.api;

import com.reborn.server.domain.job.application.JobPostSearchService;
import com.reborn.server.domain.job.application.JobPostService;
import com.reborn.server.domain.job.dto.JobPostDetailDto;
import com.reborn.server.domain.job.dto.JobPostSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/job-post")
@RequiredArgsConstructor
public class JobPostApi {

    @Autowired
    private JobPostService jobPostService;

    private final JobPostSearchService jobPostSearchService;

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
}
