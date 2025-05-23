package com.example.deployment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadTimeForChange {
    private String team;
    private LocalDate createdDate;
    private LocalDate deployedDate;
    private String interval;
    private Double leadTimeHours;
} 