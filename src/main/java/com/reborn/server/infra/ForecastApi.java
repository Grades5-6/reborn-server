package com.reborn.server.infra;

import com.reborn.server.domain.job.application.JobPostService;
import com.reborn.server.domain.job.domain.JobPost;
import com.reborn.server.domain.job.dto.JobPostDto;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/job-post")
@RequiredArgsConstructor
public class ForecastApi {
    private static final Logger logger = LoggerFactory.getLogger(ForecastApi.class);

    private RestTemplate restTemplate;

    private final JobPostService jobPostService;

    @Value("${openApi.service-key}")
    private String serviceKey;

    @Value("${openApi.base-url}")
    private String baseUrl;

    // open api에서 우선 필요한 아이들 가져오기..
    @GetMapping
    public ResponseEntity<List<JobPostDto>> getJobPost(@RequestParam(value = "pageNo") String pageNo,
    @RequestParam(value = "numOfRows") String numOfRows
    ) throws Exception {

//        HttpURLConnection urlConnection = null;
//        InputStream stream = null;
//        String result = null;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        messageConverters.add(stringConverter);
        restTemplate.setMessageConverters(messageConverters);



        String apiUrl = baseUrl + "%" + serviceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows;
        System.out.println(apiUrl);
        URI uri = new URI(apiUrl);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String xmlData = response.getBody();

//        String xmlData = restTemplate.getForObject(uri, String.class);

        xmlData = xmlData.trim(); // 앞뒤 공백 제거
        xmlData = xmlData.replaceFirst("^([\\W]+)<", "<"); // BOM 제거

        logger.info("Received XML Data: " + xmlData);


        List<JobPostDto> jobPosts = jobPostService.jobPostApiParseXml(xmlData);
        return new ResponseEntity<>(jobPosts, HttpStatus.OK);

    }

}