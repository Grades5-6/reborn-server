package com.reborn.server.domain.user.application;

import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import com.reborn.server.domain.user.dto.JobOnboardingDto;
import com.reborn.server.domain.user.dto.OnboardingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class OnboardingService {
    private final UserRepository userRepository;
    public User saveOnboardingInfo(OnboardingDto onboardingDto) {
        String userName = "김영숙";
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name: " + userName));
        user.updateOnboardingInfo(onboardingDto.getEmploymentStatus(), onboardingDto.getRegion(), onboardingDto.getInterestedField());
        return userRepository.save(user);
    }

    public User saveJobOnboardingData(JobOnboardingDto jobOnboardingDto){
        String userName = "김영숙";
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name: " + userName));
        user.updateJobOnboardingData(jobOnboardingDto.getSex(), jobOnboardingDto.getYear() , Arrays.toString(jobOnboardingDto.getCertificate()));
        return userRepository.save(user);
    }
}
