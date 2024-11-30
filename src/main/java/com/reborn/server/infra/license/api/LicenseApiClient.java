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
                .toUriString();
        System.out.println(url);
        ResponseEntity<LicenseResponse> response = restTemplate.getForEntity(url, LicenseResponse.class);
        System.out.println(response.getBody());
        return response.getBody().getBody().getItems().stream()
                .map(item -> LicenseResponseDto.builder()
                        .jmcd(item.getJmcd())
                        .jmfldnm(item.getJmfldnm())
                        .mdobligfldnm(item.getMdobligfldnm())
                        .obligfldnm(item.getObligfldnm())
                        .qualgbnm(item.getQualgbnm())
                        .seriesnm(item.getSeriesnm())
                        .build())
                .collect(Collectors.toList());
    }
}
