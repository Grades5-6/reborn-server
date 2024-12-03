package com.reborn.server.domain.user.application;

import com.reborn.server.domain.license.application.LicenseService;
import com.reborn.server.domain.license.domain.License;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import com.reborn.server.domain.user.dto.JobOnboardingDto;
import com.reborn.server.domain.user.dto.MainOnboardingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingService {
    private final UserRepository userRepository;
    private final LicenseService licenseService;

    public void saveMainOnboardingData(MainOnboardingDto mainOnboardingDto) {
        String userName = "김영숙";
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name: " + userName));
        user.updateOnboardingInfo(mainOnboardingDto.getEmploymentStatus(), mainOnboardingDto.getRegion(), mainOnboardingDto.getInterestedField());
        userRepository.save(user);
    }

    public void saveJobOnboardingData(JobOnboardingDto jobOnboardingDto){
        String userName = "김영숙";
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name: " + userName));

        List<License> licenses = jobOnboardingDto.getLicenses().stream()
                .map(licenseDto -> licenseService.findLicenseByJmfldnm(licenseDto.getJmfldnm())
                )
                .toList();

        user.getLicenses().addAll(licenses);
        user.updateJobOnboardingData(jobOnboardingDto.getSex(), jobOnboardingDto.getYear() , licenses);
        userRepository.save(user);
    }
}
