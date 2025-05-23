package com.example.deployment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lead_time")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadTimeForChangeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String team;
    
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    
    @Column(name = "deployed_date", nullable = false)
    private LocalDateTime deployedDate;
    
    @Column(name = "lead_time_hours", nullable = false)
    private double leadTimeHours;

    @Column(nullable = false)
    private String interval;
} 