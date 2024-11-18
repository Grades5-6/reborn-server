package com.reborn.server.domain.user.application;

import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
import com.reborn.server.domain.user.dto.response.UserMyPageResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;

    @Transactional
    public UserMyPageResponse getUserMypage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        return new UserMyPageResponse(
                user.getNickName(),
                user.getProfileImg(),
                user.getRebornTemperature(),
                user.getEmploymentStatus(),
                user.getRegion(),
                user.getInterestedField()
        );
    }

    @Transactional
    public void updateUserProfile(Long userId, String nickName, String profileImg, String employmentStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.updateUserProfile(nickName, profileImg, employmentStatus);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserInterests(Long userId, List<String> interestedField) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.updateUserInterests(interestedField);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserRegion(Long userId, String region) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        user.updateUserRegion(region);
        userRepository.save(user);
    }
}
