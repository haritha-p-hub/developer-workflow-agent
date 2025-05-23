package com.example.deployment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadTimeForChange {
    private String teamId;
    private String teamName;
    private LocalDateTime commitTime;
    private LocalDateTime deploymentTime;
    private long leadTimeInHours;
    private String commitId;
    private String deploymentId;
    private String status;
} 