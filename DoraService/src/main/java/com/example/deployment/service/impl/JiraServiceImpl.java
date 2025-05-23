package com.example.deployment.service.impl;

import com.example.deployment.service.JiraService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class JiraServiceImpl implements JiraService {

    @Value("${jira.url}")
    private String jiraUrl;

    @Value("${jira.username}")
    private String username;

    @Value("${jira.api-token}")
    private String apiToken;

    private final RestTemplate restTemplate;

    public JiraServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String createIssue(String title, String description, String projectKey, String issueType, String usecase) {
        String auth = username + ":" + apiToken;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedAuth);

        Map<String, Object> fields = new HashMap<>();
        fields.put("project", Map.of("key", projectKey));
        fields.put("summary", title);
        fields.put("description", description);
        fields.put("issuetype", Map.of("name", issueType));
        fields.put("customfield_10016", usecase);

        Map<String, Object> body = new HashMap<>();
        body.put("fields", fields);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.exchange(
            jiraUrl + "/rest/api/2/issue",
            HttpMethod.POST,
            request,
            Map.class
        );

        return (String) response.getBody().get("key");
    }

    @Override
    public void addComment(String issueKey, String comment) {
        String auth = username + ":" + apiToken;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedAuth);

        Map<String, String> body = new HashMap<>();
        body.put("body", comment);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        restTemplate.exchange(
            jiraUrl + "/rest/api/2/issue/" + issueKey + "/comment",
            HttpMethod.POST,
            request,
            Map.class
        );
    }

    @Override
    public void transitionIssue(String issueKey, String transitionName) {
        String auth = username + ":" + apiToken;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Basic " + encodedAuth);

        Map<String, Object> transition = new HashMap<>();
        transition.put("name", transitionName);

        Map<String, Object> body = new HashMap<>();
        body.put("transition", transition);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        restTemplate.exchange(
            jiraUrl + "/rest/api/2/issue/" + issueKey + "/transitions",
            HttpMethod.POST,
            request,
            Map.class
        );
    }
} 