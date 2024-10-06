package com.reborn.server.domain.hobby.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hobbies")
@RequiredArgsConstructor
@Tag(name = "Hobby", description = "Hobby API")
public class HobbyApi {
}
