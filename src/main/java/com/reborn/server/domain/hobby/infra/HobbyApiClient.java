package com.reborn.server.domain.hobby.infra;

import com.reborn.server.domain.hobby.application.HobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HobbyApiClient {

    private final HobbyService hobbyService;

    @Value("${openApi.service-key}")
    private String serviceKey;

    @Value("${openApi.url}")
    private String url;

    @GetMapping("/fetch-hobbies")
    public ResponseEntity<String> callHobbyApi() {
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        String result = null;
        int currentCount;
        boolean hasDataMore = true;

        int perPage = 1000; // 페이지당 데이터 수
        int page = 1;


        while (hasDataMore) {
            String urlStr = url +
                    "page=" + page +
                    "&perPage=" + perPage +
                    "&serviceKey=" + serviceKey;

            try {
                URL url = new URL(urlStr);

                urlConnection = (HttpURLConnection) url.openConnection();
                stream = getNetworkConnection(urlConnection);
                result = readStreamToString(stream);

                if (stream != null) stream.close();

                currentCount = hobbyService.parseHobbyDtosAndSave(result);

                if (currentCount < perPage)
                    hasDataMore = false;

                page++;

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /* URLConnection 을 전달받아 연결정보 설정 후 연결, 연결 후 수신한 InputStream 반환 */
    private InputStream getNetworkConnection(HttpURLConnection urlConnection) throws IOException {
        urlConnection.setConnectTimeout(3000);
        urlConnection.setReadTimeout(3000);
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoInput(true);

        if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new IOException("HTTP error code : " + urlConnection.getResponseCode());
        }

        return urlConnection.getInputStream();
    }

    /* InputStream을 전달받아 문자열로 변환 후 반환 */
    private String readStreamToString(InputStream stream) throws IOException {
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

        String readLine;
        while ((readLine = br.readLine()) != null) {
            result.append(readLine + "\n\r");
        }

        br.close();

        return result.toString();
    }
}