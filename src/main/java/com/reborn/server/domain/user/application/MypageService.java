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

    String userName = "김영숙";

    @Transactional
    public UserMyPageResponse getUserMypage() {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userName));

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
    public void updateUserProfile(String nickName, String profileImg, String employmentStatus) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userName));

        user.updateUserProfile(nickName, profileImg, employmentStatus);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserInterests(List<String> interestedField) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userName));

        user.updateUserInterests(interestedField);
        userRepository.save(user);
    }

    @Transactional
    public void updateUserRegion(String region) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userName));

        user.updateUserRegion(region);
        userRepository.save(user);
    }
}
