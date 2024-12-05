package com.reborn.server.domain.job.application;

import com.reborn.server.domain.job.api.JobPostApi;
import com.reborn.server.domain.job.dao.JobPostDetailRepository;
import com.reborn.server.domain.job.dao.JobPostRepository;
import com.reborn.server.domain.job.domain.JobPost;
import com.reborn.server.domain.job.domain.JobPostDetail;
import com.reborn.server.domain.job.dto.response.JobPostDetailDto;
import com.reborn.server.domain.job.dto.response.JobPostDto;
import com.reborn.server.domain.job.dto.response.JobResponseDto;
import com.reborn.server.domain.user.dao.UserRepository;
import com.reborn.server.domain.user.domain.User;
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
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    private final JobPostDetailRepository jobPostDetailRepository;
    private final String userName = "김영숙";

    private static final Logger logger = LoggerFactory.getLogger(JobPostApi.class);
    private final UserRepository userRepository;

    // saveAll
    public void saveJobPosts(List<JobPostDto> jobPostDtos) {
        // 중복 제거 후 한 번에 저장
        List<JobPost> jobPostsToSave = jobPostDtos.stream()
                .filter(jobDto -> !jobPostRepository.existsByJobId(jobDto.getJobId())) // 중복 체크
                .map(JobPost::new) // DTO를 엔티티로 변환
                .collect(Collectors.toList());

        // saveAll로 한 번에 저장
        if (!jobPostsToSave.isEmpty()) {
            jobPostRepository.saveAll(jobPostsToSave);
        }
    }

    // api xml 데이터 파싱 및 태그로 저장
    public List<JobPostDto> jobPostApiParseXml(String xmlData) throws Exception {

        List<JobPostDto> jobPostList = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xmlData));
        Document document = builder.parse(is);

        NodeList items = document.getElementsByTagName("item");

        LocalDate today = LocalDate.now();
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyyMMdd");

        // items 개수만큼 반복하면서 저장
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

    public String getElementValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }

    // 직업 api
    public void callJobData(RestTemplate restTemplate, String baseUrl, String serviceKey) throws Exception {
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
        int consecutiveEmptyPages = 0; // 연속으로 저장된 데이터가 없는 페이지 수

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

            // 받아온 데이터 파싱해서 saveJobPost 호출
            List<JobPostDto> jobPostDtos = jobPostApiParseXml(xmlData);

            if (jobPostDtos.isEmpty()) {
                consecutiveEmptyPages++; // 저장된 데이터가 없으면 카운트 증가
            } else {
                saveJobPosts(jobPostDtos); // 데이터 저장
                consecutiveEmptyPages = 0; // 저장된 데이터가 있으면 카운트 초기화
            }

            // 두 페이지 연속으로 저장된 데이터가 없으면 중단
            if (consecutiveEmptyPages >= 2) {
                logger.info("No new data for next two pages. Stopping API calls.");
                break;
            }

            // 더 이상 저장할 페이지 정보가 없으면 멈춤
            int totalCount = getTotalCountFromXml(xmlData);
            if (pageNo * numOfRows >= totalCount) {
                hasMoreData = false;
            } else {
                pageNo++;
            }
        }

    }

    private int getTotalCountFromXml(String xmlData) throws Exception {
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

    // 마감 기한 안 지난 공고 필터링
    public void deleteExpiredJobPosts() {
        LocalDate today = LocalDate.now();
        jobPostRepository.deleteByEndBefore(today.toString());
    }

    // 직업 상세 정보 파싱
    public JobPostDetailDto getJobPostDetail(RestTemplate restTemplate, String detailUrl, String serviceKey, String jobId) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        messageConverters.add(stringConverter);
        restTemplate.setMessageConverters(messageConverters);

        String apiUrl = detailUrl + "%" + serviceKey + "&id=" + jobId;
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
            String jobTitle = this.getElementValue(itemElement, "wantedTitle");
            String hmUrl = this.getElementValue(itemElement, "homepage");
            String companyName = this.getElementValue(itemElement, "plbizNm");
            String workAddr = this.getElementValue(itemElement, "plDetAddr");

            return JobPostDetailDto.builder()
                    .jobTitle(jobTitle)
                    .companyName(companyName)
                    .workAddr(workAddr)
                    .hmUrl(hmUrl)
                    .build();
        } else {
            throw new Exception("No details for jobId: " + jobId);
        }
    }

    // 직업 상세 정보 db에 저장
    public void saveJobDetail(RestTemplate restTemplate, String serviceKey, String detailUrl) {
        List<JobPost> jobList = jobPostRepository.findAll();
        List<JobPostDetail> jobDetailsToSave = jobList.stream()
                .map(jobPost -> {
                    try {
                        if(!jobPostDetailRepository.existsByJobId(jobPost.getJobId())) {
                            JobPostDetailDto jobDetailDto = getJobPostDetail(restTemplate, detailUrl, serviceKey, jobPost.getJobId());
                            return new JobPostDetail(jobPost.getJobId(), jobDetailDto);
                        } else return null;
                    } catch (Exception e) {
                        logger.error("Failed to fetch details for jobId");
                        return null;
                    }
                 })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!jobDetailsToSave.isEmpty()) {
            jobPostDetailRepository.saveAll(jobDetailsToSave);
            logger.info("job details saved successfully.");
        } else {
            logger.info("No job details to save.");
        }
    }

    public List<JobResponseDto> getAllJobDetails(){
        User user = userRepository.findByName(userName).orElseThrow();

        List<JobPostDetail> jobPostDetails = jobPostDetailRepository.findAll();
        return  jobPostDetails.stream()
                .filter(jobPostDetail -> jobPostDetail.getWorkAddr().contains(user.getRegion()))
                .map(jobPostDetail -> JobResponseDto.builder()
                .workAddr(jobPostDetail.getWorkAddr())
                .jobTitle(jobPostDetail.getJobTitle())
                .hmUrl(jobPostDetail.getHmUrl())
                .companyName(jobPostDetail.getCompanyName())
                .build()).collect(Collectors.toList());
    }
}


