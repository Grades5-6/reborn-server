package com.reborn.server.domain.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "certificate")
@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;
    private String agency; // 발급 기관
    private LocalDate issueDate; // 발급일
    private LocalDate expiryDate; // 만료일

    @Builder
    public Certificate(String name, String agency, LocalDate issueDate, LocalDate expiryDate){
        this.name=name;
        this.agency=agency;
        this.issueDate=issueDate;
        this.expiryDate=expiryDate;
    }

    public static Certificate of(String name, String agency, LocalDate issueDate, LocalDate expiryDate) {
        return Certificate.builder()
                .name(name)
                .agency(agency)
                .issueDate(issueDate)
                .expiryDate(expiryDate)
                .build();
    }

}
