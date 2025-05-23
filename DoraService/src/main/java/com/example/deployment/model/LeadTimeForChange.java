package com.example.deployment.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Team name is required")
    private String team;

    @NotNull(message = "Created date is required")
    private LocalDateTime createdDate;

    @NotNull(message = "Deployed date is required")
    private LocalDateTime deployedDate;

    private double leadTimeHours;

    @NotBlank(message = "Interval is required")
    private String interval;
} 