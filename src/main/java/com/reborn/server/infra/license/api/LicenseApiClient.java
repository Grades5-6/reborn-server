package com.reborn.server.infra.license.api;

import com.reborn.server.domain.license.dto.response.LicenseResponseDto;
import com.reborn.server.infra.license.domain.LicenseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class LicenseApiClient {
    @Value("${openApi.license-key}")
    private String serviceKey;

    @Value("${openApi.license-url}")
    private String baseUrl;
    private final RestTemplate restTemplate;

    public LicenseApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        restTemplate.getMessageConverters().add(new MappingJackson2XmlHttpMessageConverter());
    }

    public List<LicenseResponseDto> fetchLicenses() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .toUriString();;

        ResponseEntity<LicenseResponse> response = restTemplate.getForEntity(url, LicenseResponse.class);

        return Objects.requireNonNull(response.getBody()).getBody().getItems().stream()
                .map(item -> LicenseResponseDto.builder()
                        .jmfldnm(item.getJmfldnm())
                        .seriesnm(item.getSeriesnm())
                        .build())
                .collect(Collectors.toList());
    }
}
