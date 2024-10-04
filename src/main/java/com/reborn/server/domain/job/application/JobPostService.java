package com.reborn.server.domain.job.application;

import com.reborn.server.domain.job.dao.JobPostRepository;
import com.reborn.server.domain.job.domain.JobPost;
import com.reborn.server.domain.job.dto.JobPostDto;
import com.reborn.server.infra.ForecastApi;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    private static final Logger logger = LoggerFactory.getLogger(ForecastApi.class);


    // db에 저장
    public void saveJobPosts(List<JobPostDto> jobPostDtos) {
        for (JobPostDto jobDto : jobPostDtos) {
            if (!jobPostRepository.existsByJobId(jobDto.getJobId())) {
                JobPost jobPost = new JobPost(jobDto);
                jobPostRepository.save(jobPost);
            }
        }
    }

    // xml 데이터 파싱
    public List<JobPostDto> jobPostApiParseXml(String xmlData) throws Exception {

        List<JobPostDto> jobPostList = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlData));
        Document document = builder.parse(is);

        NodeList items = document.getElementsByTagName("item");

        LocalDate today = LocalDate.now();
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (int i = 0; i < items.getLength(); i++) {
            Node itemNode = items.item(i);

            if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                Element itemElement = (Element) itemNode;

                String jobId = getElementValue(itemElement, "jobId");
                String jobName = getElementValue(itemElement, "recrtTitle");
                String companyName = getElementValue(itemElement, "oranNm");
                String workLocation = getElementValue(itemElement, "workPlcNm");
                String status = getElementValue(itemElement, "deadline");
                String start = getElementValue(itemElement, "frDd");
                String end = getElementValue(itemElement,"toDd");

                LocalDate endDate = LocalDate.parse(end, dateTime);
                if(!endDate.isBefore(today)){
                    JobPostDto jobPostDto = new JobPostDto(jobId, jobName, companyName, workLocation, status, start, end);
                    jobPostList.add(jobPostDto);

                }
            }
        }

        return jobPostList;

    }

    private String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }

    // 데이터 동기화
    public void syncJobData(RestTemplate restTemplate, String baseUrl, String serviceKey) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        messageConverters.add(stringConverter);
        restTemplate.setMessageConverters(messageConverters);


        int pageNo = 1;
        int numOfRows = 100;
        boolean hasMoreData = true;

        while (hasMoreData) {
            String apiUrl = baseUrl + "%" + serviceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows;
            System.out.println(apiUrl);
            URI uri = new URI(apiUrl);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
            String xmlData = response.getBody();

            xmlData = xmlData.trim(); // 앞뒤 공백 제거
            xmlData = xmlData.replaceFirst("^([\\W]+)<", "<"); // BOM 제거

            logger.info("Received XML Data: " + xmlData);

            // 받아온 데이터 파싱해서 저장
            List<JobPostDto> jobPostDtos = jobPostApiParseXml(xmlData);
            saveJobPosts(jobPostDtos);

            int totalCount = getTotalCountFromXml(xmlData);
            if (pageNo * numOfRows >= totalCount) {
                hasMoreData = false;
            } else {
                pageNo++;
            }
        }
    }

    public int getTotalCountFromXml(String xmlData) throws Exception {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlData));
            Document document = builder.parse(is);

            NodeList totalCountNodeList = document.getElementsByTagName("totalCount");
            if (totalCountNodeList.getLength() > 0) {
                return Integer.parseInt(totalCountNodeList.item(0).getTextContent());
            } else {
                throw new Exception("No totalCount in the XML data");
            }
        }

    }
