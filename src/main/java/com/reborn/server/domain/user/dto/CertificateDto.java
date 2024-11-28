package com.reborn.server.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CertificateDto {
    private String name; // 자격증 이름
    private String agency; // 발급 기관
    private LocalDate issueDate; // 발급일
    private LocalDate expiryDate; // 만료일
}
