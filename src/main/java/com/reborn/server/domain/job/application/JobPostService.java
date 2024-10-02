package com.reborn.server.domain.job.application;

import com.reborn.server.domain.job.dao.JobPostRepository;
import com.reborn.server.domain.job.domain.JobPost;
import com.reborn.server.domain.job.dto.JobPostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostService {

    private final JobPostRepository jobPostRepository;

    @Transactional
    public List<JobPostDto> jobPostApiParseXml(String xmlData) throws Exception {
        List<JobPostDto> jobPostList = new ArrayList<>();
        List<JobPost> jobPostsToSave = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Document document = builder.parse(new InputSource(new StringReader(xmlData)));

        InputSource is = new InputSource(new StringReader(xmlData));
        Document document = builder.parse(is);

        NodeList items = document.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {
            Node itemNode = items.item(i);

            if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                Element itemElement = (Element) itemNode;

                JobPostDto jobPostDto;
                JobPost jobPost;

                String jobId = getElementValue(itemElement, "jobId");

                String jobName = getElementValue(itemElement, "recrtTitle");
                String companyName = getElementValue(itemElement, "oranNm");
                String workLocation = getElementValue(itemElement, "workPlcNm");
                String deadline = getElementValue(itemElement, "deadline");
                String start = getElementValue(itemElement, "toDd");

                jobPostDto = new JobPostDto(jobId, jobName, companyName, workLocation, deadline, start);
                jobPostList.add(jobPostDto);

                jobPost = new JobPost(jobId, jobName, companyName, workLocation, deadline, start);
                jobPostsToSave.add(jobPost);
            }
        }

        jobPostRepository.saveAll(jobPostsToSave);
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
}
