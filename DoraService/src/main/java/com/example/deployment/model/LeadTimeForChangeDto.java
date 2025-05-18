package com.example.deployment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeadTimeForChangeDto {
    private String interval;
    private double leadTime;
    private int changes;
} 