package com.reborn.server.domain.job.application;

import com.reborn.server.domain.job.dao.JobPostDetailRepository;
import com.reborn.server.domain.job.domain.JobPostDetail;
import com.reborn.server.domain.job.dto.JobPostSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostSearchService {
    private final JobPostDetailRepository jobPostDetailRepository;

    public List<JobPostSearchDto> searchJobPosts(String keyword) {
        // keyword가 null이거나 비어있으면 빈 리스트 반환
        if (keyword == null || keyword.isEmpty()) {
            return List.of();
        }

        // JobPostDetail을 검색하여 필터링
        List<JobPostDetail> jobPosts = jobPostDetailRepository.findAll(); // 모든 직업공고를 가져옴

        return jobPosts.stream()
                .filter(jobPost -> jobPost.getJobTitle().contains(keyword)) // jobTitle에 keyword가 포함되는지 필터링
                .map(jobPost -> JobPostSearchDto.builder() // JobPostSearchDto로 변환
                        .jobId(jobPost.getJobId())
                        .jobTitle(jobPost.getJobTitle())
                        .companyName(jobPost.getCompanyName())
                        .workAddr(jobPost.getWorkAddr())
                        .hmUrl(jobPost.getHmUrl())
                        .build())
                .collect(Collectors.toList()); // 리스트로 수집
    }
}
