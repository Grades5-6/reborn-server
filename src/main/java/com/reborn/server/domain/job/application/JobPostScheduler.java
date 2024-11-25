package com.reborn.server.domain.job.application;

import com.reborn.server.domain.job.dao.JobPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정
    public void updateJobPosts() throws Exception {
        jobPostService.syncJobData(restTemplate, baseUrl, serviceKey);
        jobPostService.deleteExpiredJobPosts();
    }

}
