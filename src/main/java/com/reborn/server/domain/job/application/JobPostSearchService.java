package com.reborn.server.domain.job.application;

import com.reborn.server.domain.job.dao.JobPostDetailRepository;
import com.reborn.server.domain.job.domain.JobPostDetail;
import com.reborn.server.domain.job.dto.response.JobResponseDto;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
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

    public List<JobResponseDto> getJobPostsByLicenses(String jmfldnm) {
        User user = userRepository.findByName(userName).orElseThrow();
        String region = user.getRegion();

        return calculateScoreAndFilter(jobPost -> {
            int score = 0;
                if(jobPost.getWorkAddr().contains(region)) {
                    for (char ch : jmfldnm.substring(0, jmfldnm.length() - 2).toCharArray()) {
                        String character = String.valueOf(ch);
                        if (jobPost.getJobTitle().contains(character)) {
                            score += 5;
                        }
                        if (jobPost.getCompanyName().contains(character)) {
                            score += 3;
                        }
                    }
            }
            return score;
        });
    }

    public List<JobResponseDto> getRecommendJobPost(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return calculateScoreAndFilter(jobPost -> {
            int score = 0;
            int currentYear = LocalDate.now().getYear(); // 현재 연도 가져오기
            int userAge = currentYear - user.getYear();

            // 사용자의 지역과 일자리 공고의 위치 비교
            if (jobPost.getWorkAddr().contains(user.getRegion())) {
                score += 15;
            }

            // 사용자의 나이와 일자리 공고의 연령 제한 비교
            if (isAgeEligible(userAge, jobPost.getAgeLim(), jobPost.getAge())) {
                score += 10;
            }

            // 사용자 관심사와 공고 제목, 세부 내용, 회사 이름에서의 검사 및 점수 추가
            for (String interest : user.getInterestedField()) {
                int titleCount = countOccurrences(jobPost.getJobTitle(), interest);
                int detailCount = countOccurrences(jobPost.getDetailCont(), interest);
                int companyCount = countOccurrences(jobPost.getCompanyName(), interest);

                // 각 관심사가 등장할 때마다 점수 부여
                score += titleCount * 5; // 제목에서의 점수
                score += detailCount * 3; // 세부 내용에서의 점수
                score += companyCount * 2; // 회사 이름에서의 점수
            }
            return score;
        });

    }

    private boolean isAgeEligible(int userAge, String ageLim, String age) {

        int maxAge = Integer.MAX_VALUE;
        int minAge = 0;

        if (ageLim != null && !ageLim.isEmpty()) {
            maxAge = Integer.parseInt(ageLim);
        }

        if (age != null && !age.isEmpty()) {
            minAge = Integer.parseInt(age); // 공고에서 요구하는 최소 나이
        }

        return userAge >= minAge && userAge <= maxAge;
    }

    // 문자열에서 특정 문자열의 등장 횟수를 카운트
    private int countOccurrences(String text, String substring) {
        if (text == null || substring == null || substring.isEmpty()) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = text.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length(); // 다음 검색 위치로 이동
        }

        return count;
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
                .filter(entry -> entry.getValue() >= 10) // 점수가 0보다 큰 경우만 필터링
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
