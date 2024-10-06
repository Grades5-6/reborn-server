package com.reborn.server.domain.hobby.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class HobbyResponse {
    private Long id;
    private String cityDistrictName; // 시군구 명칭
    private String provinceName; // 시도 명칭
    private String category; // 카테고리
    private int classDuration; // 클래스 시간
    private int totalCost; // 클래스 요금
    private String classParticipants; // 클래스 인원
    private String curriculum; // 클래스 커리큘럼
    private String classTitle; // 클래스 타이틀


    public static HobbyResponse of(Long id, String cityDistrictName, String provinceName, String category, int classDuration, int totalCost, String classParticipants, String curriculum, String classTitle) {
        return HobbyResponse.builder()
                .id(id)
                .cityDistrictName(cityDistrictName)
                .provinceName(provinceName)
                .category(category)
                .classDuration(classDuration)
                .totalCost(totalCost)
                .classParticipants(classParticipants)
                .curriculum(curriculum)
                .classTitle(classTitle)
                .build();
    }
}
