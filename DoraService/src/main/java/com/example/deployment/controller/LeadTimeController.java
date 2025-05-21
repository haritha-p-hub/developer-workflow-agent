package com.example.deployment.controller;

import com.example.deployment.model.LeadTimeMetrics;
import com.example.deployment.service.LeadTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/metrics")
public class LeadTimeController {

    @Autowired
    private LeadTimeService leadTimeService;

    @GetMapping("/lead-time")
    public ResponseEntity<LeadTimeMetrics> getLeadTimeMetrics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String team,
            @RequestParam(defaultValue = "daily") String interval) {
        
        LeadTimeMetrics metrics = leadTimeService.getLeadTimeMetrics(startDate, endDate, team, interval);
        return ResponseEntity.ok(metrics);
    }
} 