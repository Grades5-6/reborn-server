package com.reborn.server.domain.job.application;

import com.reborn.server.domain.job.dao.JobPostDetailRepository;
import com.reborn.server.domain.job.domain.JobPostDetail;
import com.reborn.server.domain.job.dto.JobPostSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
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
                .map(jobPost -> {
                    int score = 0;
                    if (jobPost.getJobTitle().contains(keyword)) {
                        score += 10; // 제목에 포함
                    }
                    if (jobPost.getCompanyName().contains(keyword)) {
                        score += 3; // 회사 이름에 포함
                    }
                    if (jobPost.getWorkAddr().contains(keyword)) {
                        score += 3; // 지역에 포함
                    }
                    return new AbstractMap.SimpleEntry<>(jobPost, score);
                })
                .filter(entry -> entry.getValue() > 0) // 점수가 0보다 큰 경우만 필터링
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())) // 점수에 따라 정렬
                .map(entry -> {
                    JobPostDetail jobPost = entry.getKey();
                    return JobPostSearchDto.builder()
                            .jobId(jobPost.getJobId())
                            .jobTitle(jobPost.getJobTitle())
                            .companyName(jobPost.getCompanyName())
                            .workAddr(jobPost.getWorkAddr())
                            .hmUrl(jobPost.getHmUrl())
                            .build();
                })
                .collect(Collectors.toList()); // 리스트로 수집
    }
}
