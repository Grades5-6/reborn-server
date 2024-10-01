package com.reborn.server.domain.auth.util;

import com.reborn.server.domain.auth.dto.Tokens;
import com.reborn.server.domain.auth.dto.response.TokenResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    public static void setRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");  // 쿠키가 유효한 경로 설정
        cookie.setMaxAge(5 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    public static TokenResponse setJwtResponse(HttpServletResponse response, Tokens tokenDto) {
        TokenResponse tokenResponseDto = TokenResponse.builder()
                .grantType(tokenDto.getGrantType())
                .accessToken(tokenDto.getAccessToken())
                .accessTokenExpiresIn(tokenDto.getAccessTokenExpiresIn())
                .build();

        setRefreshTokenInCookie(response, tokenDto.getRefreshToken());
        return tokenResponseDto;
    }

}
