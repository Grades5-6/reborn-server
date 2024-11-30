package com.reborn.server.domain.license.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jmcd;          // 자격코드
    private String jmfldnm;       // 자격명
    private String mdobligfldnm;  // 중분류 이름
    private String obligfldnm;    // 대분류 이름
    private String qualgbnm;      // 자격 구분 이름
    private String seriesnm;      // 시리즈 이름

    @Builder
    public License(String jmcd, String jmfldnm, String mdobligfldnm, String obligfldnm, String qualgbnm, String seriesnm) {
        this.jmcd = jmcd;
        this.jmfldnm = jmfldnm;
        this.mdobligfldnm = mdobligfldnm;
        this.obligfldnm = obligfldnm;
        this.qualgbnm = qualgbnm;
        this.seriesnm = seriesnm;
    }
}
