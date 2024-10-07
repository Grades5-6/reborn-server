package com.reborn.server.domain.job.dao;

import com.reborn.server.domain.job.domain.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    boolean existsByJobId(String jobId);

    void deleteByEndBefore(String string);
}
