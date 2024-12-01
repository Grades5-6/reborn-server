package com.reborn.server.global.config;

import com.reborn.server.domain.job.application.JobPostScheduler;
import com.reborn.server.infra.license.util.LicenseScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SetUpConfig {
    private final LicenseScheduler licenseScheduler;
    private final JobPostScheduler jobPostScheduler;

    @Bean
    public ApplicationRunner runSchedulerOnStartup() {
        return args -> {
            licenseScheduler.fetchLicenseData();
            jobPostScheduler.updateJobPosts();
        };
    }
}
