package com.reborn.server.domain.job.application;

import com.reborn.server.domain.job.dao.JobPostDetailRepository;
import com.reborn.server.domain.job.domain.JobPostDetail;
import com.reborn.server.domain.job.dto.response.JobResponseDto;
import com.reborn.server.domain.license.domain.License;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostSearchService {
    private final JobPostDetailRepository jobPostDetailRepository;
    private final UserRepository userRepository;
    private final String userName = "김영숙";

    public List<JobResponseDto> searchJobPosts(String keyword) {
        // 키워드가 없으면 빈 리스트 반환
        if (keyword == null || keyword.isEmpty()) {
            return List.of();
        }

        // 단일 키워드로 점수 계산 및 필터링
        return calculateScoreAndFilter(jobPost -> {
            int score = 0;
            if (jobPost.getJobTitle().contains(keyword)) {
                score += 20; // 제목에 포함
            }
            if (jobPost.getCompanyName().contains(keyword)) {
                score += 10; // 회사 이름에 포함
            }
            if (jobPost.getWorkAddr().contains(keyword)) {
                score += 10; // 지역에 포함
            }
            return score;
        });
    }

    public List<JobResponseDto> getJobPostsByLicenses() {
        User user = userRepository.findByName(userName).orElseThrow();
        List<License> licenses = user.getLicenses();
        List<String> jmfldnms = licenses.stream()
                .map(License::getJmfldnm)
                .toList();
        String region = user.getRegion();

        if (jmfldnms.isEmpty()) {
            return List.of();
        }

        return calculateScoreAndFilter(jobPost -> {
            int score = 0;
            for (String keyword : jmfldnms) {
                if(jobPost.getWorkAddr().contains(region)) {
                    for (char ch : keyword.substring(0, keyword.length() - 2).toCharArray()) {
                        String character = String.valueOf(ch);
                        if (jobPost.getJobTitle().contains(character)) {
                            score += 2;
                        }
                        if (jobPost.getCompanyName().contains(character)) {
                            score += 1;
                        }
                    }
                }
            }
            return score;
        });
    }

    /**
     * 점수 계산 및 필터링 공통 로직
     * @param scoreCalculator 점수 계산 로직
     * @return 필터링된 JobResponseDto 리스트
     */
    private List<JobResponseDto> calculateScoreAndFilter(Function<JobPostDetail, Integer> scoreCalculator) {
        List<JobPostDetail> jobPosts = jobPostDetailRepository.findAll();

        return jobPosts.stream()
                .map(jobPost -> new AbstractMap.SimpleEntry<>(jobPost, scoreCalculator.apply(jobPost)))
                .filter(entry -> entry.getValue() > 7) // 점수가 0보다 큰 경우만 필터링
                .sorted((a, b) -> Integer.compare(b.getValue(), a.getValue())) // 점수에 따라 정렬
                .map(entry -> {
                    JobPostDetail jobPost = entry.getKey();
                    return JobResponseDto.builder()
                            .jobTitle(jobPost.getJobTitle())
                            .companyName(jobPost.getCompanyName())
                            .workAddr(jobPost.getWorkAddr())
                            .hmUrl(jobPost.getHmUrl())
                            .build();
                })
                .collect(Collectors.toList());
    }
}
