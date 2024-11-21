package com.reborn.server.domain.user.api;

import com.reborn.server.domain.user.application.OnboardingService;
import com.reborn.server.domain.user.domain.User;
import com.reborn.server.domain.user.dto.OnboardingDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.reborn.server.global.util.ApiUtil.getUserIdFromAuthentication;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
@Tag(name = "onboarding-api")
public class OnboardingApi {
    private final OnboardingService onboardingService;

    @PostMapping("/save")
    public ResponseEntity<Void> saveOnboardingInfo(@RequestBody OnboardingDto onboardingDto){
        try {
            Long userId = getUserIdFromAuthentication();
            onboardingService.saveOnboardingInfo(onboardingDto, userId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
