package com.example.deployment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadTimeForChangeDto {
    private String team;
    private LocalDateTime createdDate;
    private LocalDateTime deployedDate;
    private double leadTimeHours;
    private String interval;

    // Additional fields for request/response specific data
    private String status;
    private String message;
    private String errorCode;
} 