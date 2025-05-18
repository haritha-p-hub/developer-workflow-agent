package com.example.deployment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeadTimeForChangeEntity {
    private String interval;
    private double leadTime;
    private int changes;
} 