package com.reborn.server.domain.hobby.domain;

import com.reborn.server.domain.hobby.dto.HobbyDto;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String legalTownName; // 법정읍면동명칭
    private String cityDistrictName; // 시군구 명칭
    private String cityDistrictAddress; // 시군구 주소
    private String provinceName; // 시도 명칭
    private String lastUpdatedDate; // 최종작성일
    private String category; // 카테고리
    private int classDuration; // 클래스 시간
    private int totalCost; // 클래스 요금
    private String classParticipants; // 클래스 인원

    @Column(name = "curriculum", length = 2000)
    private String curriculum; // 클래스 커리큘럼

    @Column(name = "class_title", unique = true) // classTitle을 유니크로 설정
    private String classTitle; // 클래스 타이틀

    @Builder
    public Hobby(String legalTownName, String cityDistrictName, String cityDistrictAddress, String provinceName, String lastUpdatedDate, String category, int classDuration, int totalCost, String classParticipants, String curriculum, String classTitle) {
        this.legalTownName = legalTownName;
        this.cityDistrictName = cityDistrictName;
        this.cityDistrictAddress = cityDistrictAddress;
        this.provinceName = provinceName;
        this.lastUpdatedDate = lastUpdatedDate;
        this.category = category;
        this.classDuration = classDuration;
        this.totalCost = totalCost;
        this.classParticipants = classParticipants;
        this.curriculum = curriculum;
        this.classTitle = classTitle;
    }

    public static Hobby of(String legalTownName, String cityDistrictName, String cityDistrictAddress, String provinceName, String lastUpdatedDate, String category, int classDuration, int totalCost, String classParticipants, String curriculum, String classTitle){
        return Hobby.builder()
                .legalTownName(legalTownName)
                .cityDistrictName(cityDistrictName)
                .cityDistrictAddress(cityDistrictAddress)
                .provinceName(provinceName)
                .lastUpdatedDate(lastUpdatedDate)
                .category(category)
                .classDuration(classDuration)
                .totalCost(totalCost)
                .classParticipants(classParticipants)
                .curriculum(curriculum)
                .classTitle(classTitle)
                .build();
    }

    public static Hobby from(HobbyDto hobbyDto){
        return Hobby.builder()
                .legalTownName(hobbyDto.getLegalTownName())
                .cityDistrictName(hobbyDto.getCityDistrictName()) // 시군구 명칭
                .cityDistrictAddress(hobbyDto.getCityDistrictAddress()) // 시군구 주소
                .provinceName(hobbyDto.getProvinceName()) // 시도 명칭
                .lastUpdatedDate(hobbyDto.getLastUpdatedDate()) // 최종작성일
                .category(hobbyDto.getCategory()) // 여가활동
                .classDuration(hobbyDto.getClassDuration()) // 클래스 시간
                .totalCost(hobbyDto.getTotalCost()) // 클래스 요금
                .classParticipants(hobbyDto.getClassParticipants()) // 클래스 인원
                .curriculum(hobbyDto.getCurriculum()) // 클래스 커리큘럼
                .classTitle(hobbyDto.getClassTitle()) // 클래스 타이틀
                .build(); // 빌드하여 Hobby 객체 생성
    }
}
