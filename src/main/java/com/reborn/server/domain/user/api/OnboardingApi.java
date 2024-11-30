package com.reborn.server.domain.user.api;

import com.reborn.server.domain.user.application.OnboardingService;
import com.reborn.server.domain.user.dto.JobOnboardingDto;
import com.reborn.server.domain.user.dto.MainOnboardingDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/onboarding")
@RequiredArgsConstructor
@Tag(name = "onboarding-api")
public class OnboardingApi {
    private final OnboardingService onboardingService;

    @PostMapping()
    public ResponseEntity<Void> saveMainOnboardingInfo(@RequestBody MainOnboardingDto mainOnboardingDto){
        try {
            onboardingService.saveMainOnboardingData(mainOnboardingDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/jobs")
    public ResponseEntity<Void> saveJobOnboardingData(@RequestBody JobOnboardingDto jobOnboardingDto){
        try {
            onboardingService.saveJobOnboardingData(jobOnboardingDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
