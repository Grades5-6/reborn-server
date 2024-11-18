package com.reborn.server.domain.user.api;

import com.reborn.server.domain.user.application.MypageService;
import com.reborn.server.domain.user.dto.request.UserInterestsUpdateRequest;
import com.reborn.server.domain.user.dto.request.UserProfileUpdateRequest;
import com.reborn.server.domain.user.dto.request.UserRegionUpdateRequest;
import com.reborn.server.domain.user.dto.response.UserMyPageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.reborn.server.global.util.ApiUtil.getUserIdFromAuthentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/mypage")
@Tag(name = "mypage-api")
public class MypageApi {

    private final MypageService mypageService;

    // 마이페이지 조회
    @GetMapping()
    public ResponseEntity<UserMyPageResponse> getUserProfile() {
        try {
            Long userId = getUserIdFromAuthentication();
            UserMyPageResponse userMyPageResponse = mypageService.getUserMypage(userId);
            return ResponseEntity.ok(userMyPageResponse);

        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    // 프로필 수정
    @PatchMapping()
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfileUpdateRequest userProfileUpdateRequest) {
        try {
            Long userId = getUserIdFromAuthentication();
            mypageService.updateUserProfile(userId,
                    userProfileUpdateRequest.getNickName(),
                    userProfileUpdateRequest.getProfileImg(),
                    userProfileUpdateRequest.getEmploymentStatus());
            return ResponseEntity.ok("프로필이 수정되었습니다.");

        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    // 관심 분야 수정
    @PatchMapping("/interests")
    public ResponseEntity<String> updateUserInterests(@RequestBody UserInterestsUpdateRequest userInterestsUpdateRequest) {
        try {
            Long userId = getUserIdFromAuthentication();
            mypageService.updateUserInterests(userId, userInterestsUpdateRequest.getInterestedField());
            return ResponseEntity.ok("관심 분야가 수정되었습니다.");

        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    // 동네 수정
    @PatchMapping("/region")
    public ResponseEntity<String> updateUserRegion(@RequestBody UserRegionUpdateRequest userRegionUpdateRequest) {
        try {
            Long userId = getUserIdFromAuthentication();
            mypageService.updateUserRegion(userId, userRegionUpdateRequest.getRegion());
            return ResponseEntity.ok("나의 동네가 수정되었습니다.");

        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
