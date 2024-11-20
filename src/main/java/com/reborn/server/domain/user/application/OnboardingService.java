package com.reborn.server.domain.user.application;

import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import com.reborn.server.domain.user.dto.OnboardingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnboardingService {
    private final UserRepository userRepository;
    public User saveOnboardingInfo(OnboardingDto onboardingDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        user.updateOnboardingInfo(onboardingDto.getEmploymentStatus(), onboardingDto.getRegion(), onboardingDto.getInterestedField());
        return userRepository.save(user);

    }
}
