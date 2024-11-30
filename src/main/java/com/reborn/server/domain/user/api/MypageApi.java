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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/mypage")
@Tag(name = "mypage-api")
public class MypageApi {

    private final MypageService mypageService;

    // 마이페이지 조회
    @GetMapping()
    public ResponseEntity<UserMyPageResponse> getUserProfile() {
        try {
            UserMyPageResponse userMyPageResponse = mypageService.getUserMypage();
            return ResponseEntity.ok(userMyPageResponse);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }

    // 프로필 수정
    @PutMapping()
    public ResponseEntity<String> updateUserProfile(@RequestBody UserProfileUpdateRequest userProfileUpdateRequest) {
        try {
            mypageService.updateUserProfile(
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
    @PutMapping("/interests")
    public ResponseEntity<String> updateUserInterests(@RequestBody UserInterestsUpdateRequest userInterestsUpdateRequest) {
        try {
            mypageService.updateUserInterests(userInterestsUpdateRequest.getInterestedField());
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
            mypageService.updateUserRegion(userRegionUpdateRequest.getRegion());
            return ResponseEntity.ok("나의 동네가 수정되었습니다.");

        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e){
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
