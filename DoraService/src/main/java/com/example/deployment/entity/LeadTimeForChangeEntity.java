package com.example.deployment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "lead_time_for_change")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadTimeForChangeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String team;
    private LocalDate createdDate;
    private LocalDate deployedDate;
    private String interval;
    private Double leadTimeHours;
} 