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
public class LeadTimeForChangeModel {
    private String team;
    private LocalDateTime createdDate;
    private LocalDateTime deployedDate;
    private double leadTimeHours;
    private String interval;
} 