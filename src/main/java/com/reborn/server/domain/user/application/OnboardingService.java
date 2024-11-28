package com.reborn.server.domain.user.application;

import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.Certificate;
import com.reborn.server.domain.user.domain.User;
import com.reborn.server.domain.user.dto.JobOnboardingDto;
import com.reborn.server.domain.user.dto.MainOnboardingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OnboardingService {
    private final UserRepository userRepository;
    public User saveMainOnboardingData(MainOnboardingDto mainOnboardingDto) {
        String userName = "김영숙";
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name: " + userName));
        user.updateOnboardingInfo(mainOnboardingDto.getEmploymentStatus(), mainOnboardingDto.getRegion(), mainOnboardingDto.getInterestedField());
        return userRepository.save(user);
    }

    public User saveJobOnboardingData(JobOnboardingDto jobOnboardingDto){
        String userName = "김영숙";
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name: " + userName));

        // JobOnboardingDto.CertificateDto 배열을 Certificate 리스트로 변환
        List<Certificate> certificates = jobOnboardingDto.getCertificate().stream()
                        .map(certificateDto -> Certificate.of(
                                certificateDto.getName(),
                                certificateDto.getAgency(),
                                certificateDto.getIssueDate(),
                                certificateDto.getExpiryDate()
                        ))
                                .collect(Collectors.toList());
        user.getCertificate().addAll(certificates);
        user.updateJobOnboardingData(jobOnboardingDto.getSex(), jobOnboardingDto.getYear() , certificates);
        return userRepository.save(user);
    }
}
