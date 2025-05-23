package com.example.deployment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lead_time_for_change")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadTimeForChangeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "team_id", nullable = false)
    private String teamId;
    
    @Column(name = "team_name", nullable = false)
    private String teamName;
    
    @Column(name = "commit_time", nullable = false)
    private LocalDateTime commitTime;
    
    @Column(name = "deployment_time", nullable = false)
    private LocalDateTime deploymentTime;
    
    @Column(name = "lead_time_in_hours", nullable = false)
    private long leadTimeInHours;
    
    @Column(name = "commit_id", nullable = false)
    private String commitId;
    
    @Column(name = "deployment_id", nullable = false)
    private String deploymentId;
    
    @Column(name = "status", nullable = false)
    private String status;
} 