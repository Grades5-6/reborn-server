package com.reborn.server.domain.job.application;

import com.reborn.server.domain.job.dao.JobPostRepository;
import com.reborn.server.domain.job.dto.JobPostDetailDto;
import com.reborn.server.infra.ForecastApi;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostDetailService {
    private final JobPostRepository jobPostRepository;
    private final JobPostService jobPostService;
    private static final Logger logger = LoggerFactory.getLogger(ForecastApi.class);


    // 직업 상세 정보 조회
    public JobPostDetailDto getJobPostDetail(RestTemplate restTemplate, String detailUrl, String serviceKey, String jobId) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        messageConverters.add(stringConverter);
        restTemplate.setMessageConverters(messageConverters);

        String apiUrl = detailUrl + "%" + serviceKey + "&id=" + jobId;
        System.out.println(apiUrl);
        URI uri = new URI(apiUrl);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String xmlData = response.getBody();

        xmlData = xmlData.trim(); // 앞뒤 공백 제거
        xmlData = xmlData.replaceFirst("^([\\W]+)<", "<"); // BOM 제거

        logger.info("Received XML Data: " + xmlData);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlData));
        Document document = builder.parse(is);

        NodeList item = document.getElementsByTagName("item");
        if (item.getLength() > 0) {
            Element itemElement = (Element) item.item(0);

            String age = jobPostService.getElementValue(itemElement, "age");
            String ageLim = jobPostService.getElementValue(itemElement, "ageLim");
            String jobTitle = jobPostService.getElementValue(itemElement, "wantedTitle");
            String wantedNum = jobPostService.getElementValue(itemElement, "clltPrnnum");
            String start = jobPostService.getElementValue(itemElement, "frAcptDd");
            String end = jobPostService.getElementValue(itemElement, "toAcptDd");
            String detailCont = jobPostService.getElementValue(itemElement, "detCnts");
            String clerkphone = jobPostService.getElementValue(itemElement, "clerkContt");
            String hmUrl = jobPostService.getElementValue(itemElement, "homepage");
            String companyName = jobPostService.getElementValue(itemElement, "plbizNm");
            String workAddr = jobPostService.getElementValue(itemElement, "plDetAddr");

            return new JobPostDetailDto(jobId, age, ageLim, jobTitle,wantedNum ,start, end, detailCont, clerkphone, hmUrl, companyName, workAddr);
        } else {
            throw new Exception("No details for jobId: " + jobId);
        }
    }


}
