package com.reborn.server.domain.job.api;

import com.reborn.server.domain.job.application.JobPostService;
import com.reborn.server.domain.job.dto.JobPostDetailDto;
import com.reborn.server.domain.job.dto.JobPostDto;
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

    @GetMapping
    public ResponseEntity<List<JobPostDetailDto>> getAllJobPost() {
        List<JobPostDetailDto> jobPostDetail = jobPostService.getAllJobPosts();
        return ResponseEntity.ok(jobPostDetail);
    }
}
