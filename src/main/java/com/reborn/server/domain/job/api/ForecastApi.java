package com.reborn.server.domain.job.api;

import com.reborn.server.domain.job.application.JobPostService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class ForecastApi {

    private final RestTemplate restTemplate;
    private final JobPostService jobPostService;

    @Value("${openApi.service-key}")
    private String serviceKey;

    @Value("${openApi.base-url}")
    private String baseUrl;

    @GetMapping
    public ResponseEntity<Void> reloadJobPost() throws Exception {
        jobPostService.syncJobData(restTemplate, baseUrl, serviceKey);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}



