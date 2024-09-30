package com.reborn.server.domain.auth.api;

import com.reborn.server.domain.auth.application.OauthLoginService;
import com.reborn.server.domain.auth.dto.Tokens;
import com.reborn.server.domain.auth.dto.response.TokenResponse;
import com.reborn.server.domain.auth.infra.kakao.KakaoLoginParams;
import com.reborn.server.domain.auth.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthApi {

    private final OauthLoginService oauthLoginService;

    @PostMapping("/kakao")
    public ResponseEntity<?> loginKakao(@RequestBody KakaoLoginParams params, HttpServletResponse response) {
        Tokens tokens = oauthLoginService.login(params);
        TokenResponse tokenResponseDto = JwtUtil.setJwtResponse(response, tokens);
        return ResponseEntity.ok(tokenResponseDto);
    }
}
