package com.reborn.server.domain.hobby.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reborn.server.domain.hobby.dao.HobbyRepository;
import com.reborn.server.domain.hobby.domain.Hobby;
import com.reborn.server.domain.hobby.dto.HobbyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HobbyService {

    private final HobbyRepository hobbyRepository;

    public List<Hobby> saveHobbies(List<HobbyDto> hobbyDtoList) {
        List<Hobby> savedHobbies = new ArrayList<>();

        for (HobbyDto hobbyDto : hobbyDtoList) {
            Hobby hobby = Hobby.from(hobbyDto);
            try {
                // 데이터베이스에 저장
                savedHobbies.add(hobbyRepository.save(hobby)); // 여기에서 유니크 제약으로 예외 발생 가능
            } catch (DataIntegrityViolationException e) {
                // 중복된 classTitle로 인한 예외 처리
                System.out.println("Hobby with title '" + hobbyDto.getClassTitle() + "' already exists. Skipping.");
            }
        }

        System.out.println(savedHobbies.size());
        return savedHobbies; // 성공적으로 저장된 Hobby 객체 반환
    }

    public int parseHobbyDtosAndSave(String jsonResponse) {
        List<HobbyDto> hobbyDtoList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        int currentCount = 0;

        try {
            // JSON 문자열을 JsonNode로 변환
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode dataNode = rootNode.path("data"); // "data" 배열 추출
            JsonNode countNode = rootNode.path("currentCount");
            System.out.println("currentCount: " + countNode.asText());
            currentCount = countNode.asInt();

            // data 배열을 순회하여 HobbyDto 생성
            for (JsonNode hobbyNode : dataNode) {
                HobbyDto hobbyDto = HobbyDto.of(
                        hobbyNode.path("법정읍면동명칭").asText(),
                        hobbyNode.path("시군구 명칭").asText(),
                        hobbyNode.path("시군구 주소").asText(),
                        hobbyNode.path("시도 명칭").asText(),
                        hobbyNode.path("최종작성일").asText(),
                        hobbyNode.path("카테고리3").asText(),
                        hobbyNode.path("클래스 시간").asInt(),
                        hobbyNode.path("클래스 요금").asInt(),
                        hobbyNode.path("클래스 인원").asText(),
                        hobbyNode.path("클래스 커리큘럼").asText(),
                        hobbyNode.path("클래스 타이틀").asText()
                );
                hobbyDtoList.add(hobbyDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 결과 출력 test
        for (HobbyDto hobbyDto : hobbyDtoList) {
            System.out.println(hobbyDto.getClassTitle());
        }
        saveHobbies(hobbyDtoList);
        return currentCount;
    }
}
