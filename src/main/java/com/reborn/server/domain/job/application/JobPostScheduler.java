package com.reborn.server.domain.job.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class JobPostScheduler {

    private final JobPostService jobPostService;
    private final RestTemplate restTemplate;

    @Value("${openApi.base-url}")
    private String baseUrl;

    @Value("${openApi.service-key}")
    private String serviceKey;

    @Value("${openApi.detail-url}")
    private String detailUrl;


    //@Scheduled(cron = "0 0 0 * * ?") // 매일 자정
    public void updateJobPosts() throws Exception {
        jobPostService.callJobData(restTemplate, baseUrl, serviceKey);
        jobPostService.deleteExpiredJobPosts();
        jobPostService.saveJobDetail(restTemplate, serviceKey, detailUrl);


    }

}
