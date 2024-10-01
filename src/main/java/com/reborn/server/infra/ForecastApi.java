package com.reborn.server.infra;

import com.reborn.server.domain.job.domain.JobPost;
import com.reborn.server.domain.job.dto.JobPostDto;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/job-post")
public class ForecastApi {
    private RestTemplate restTemplate;

    // 수정 예정
    @Value("${openApi.serviceKey}")
    private String serviceKey;

    @Value("${openApi.baseUrl}")
    private String baseUrl;

    public ResponseEntity<List<JobPostDto>> getJobPost(@RequestParam(value = "pageNo") String pageNo,
    @RequestParam(value = "numOfRows", defaultValue = "10") String numOfRows
    ) {
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = baseUrl + "serviceKey=" + serviceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows;

        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<JobPostDto> jobPosts = new ArrayList<>();
//        try {
//            JSONObject jsonResponse = new JSONObject(Integer.parseInt(response.getBody()));
//            JSONArray jobs = jsonResponse.getJSONObject("response").getJSONArray("items");
//
//            for (int i = 0; i < jobs.length(); i++) {
//                JSONObject job = jobs.getJSONObject(i);
//                JobPost jobPost = new JobPost(
//                        job.optString("jobId"),
//                        job.optString("recrtTitle", "제목 없음"),
//                        job.optString("oranNm", "회사 이름 없음"),
//                        job.optString("workPlcNm", "근무지 없음"),
//                        job.optString("emplymShpNm", "고용형태 없음"),
//                        job.optString("toDd", "마감일 없음")
//                );
//                jobPosts.add(jobPost);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        return new ResponseEntity<>(jobPosts, HttpStatus.OK);

    }

}