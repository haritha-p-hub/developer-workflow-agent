package com.example.deployment.service;

public interface JiraService {
    String createIssue(String title, String description, String projectKey, String issueType, String usecase);
    void addComment(String issueKey, String comment);
    void transitionIssue(String issueKey, String transitionName);
} 