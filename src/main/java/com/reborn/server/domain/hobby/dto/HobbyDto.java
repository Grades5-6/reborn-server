package com.reborn.server.domain.hobby.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class HobbyDto {

    private String legalTownName; // 법정읍면동명칭
    private String cityDistrictName; // 시군구 명칭
    private String cityDistrictAddress; // 시군구 주소
    private String provinceName; // 시도 명칭
    private String lastUpdatedDate; // 최종작성일
    private String category; // 여가활동
    private int classDuration; // 클래스 시간
    private int totalCost; // 클래스 요금
    private String classParticipants; // 클래스 인원
    private String curriculum; // 클래스 커리큘럼
    private String classTitle; // 클래스 타이틀


    public static HobbyDto of(String legalTownName, String cityDistrictName, String cityDistrictAddress, String provinceName, String lastUpdatedDate, String category, int classDuration, int totalCost, String classParticipants, String curriculum, String classTitle) {
        return HobbyDto.builder()
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

}