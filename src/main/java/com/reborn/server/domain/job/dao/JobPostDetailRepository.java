package com.reborn.server.domain.job.dao;

import com.reborn.server.domain.job.domain.JobPostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostDetailRepository extends JpaRepository<JobPostDetail,Long>{
    boolean existsByJobId(String jobId);
}
