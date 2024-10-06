package com.reborn.server.domain.hobby.api;

import com.reborn.server.domain.hobby.application.HobbyService;
import com.reborn.server.domain.hobby.dto.response.HobbyResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hobbies")
@RequiredArgsConstructor
@Tag(name = "Hobby", description = "Hobby API")
public class HobbyApi {

    private final HobbyService hobbyService;

    @GetMapping() // "/hobbies" 경로로 GET 요청 시 호출
    public List<HobbyResponse> getHobbies() {
        return hobbyService.getAllHobbies(); // 서비스에서 데이터 가져와 반환
    }
}
