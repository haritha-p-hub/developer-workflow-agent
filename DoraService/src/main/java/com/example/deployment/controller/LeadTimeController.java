package com.example.deployment.controller;

import com.example.deployment.entity.LeadTime;
import com.example.deployment.service.LeadTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lead-time")
public class LeadTimeController {

    private final LeadTimeService leadTimeService;

    @Autowired
    public LeadTimeController(LeadTimeService leadTimeService) {
        this.leadTimeService = leadTimeService;
    }

    @GetMapping("/team/{team}")
    public ResponseEntity<List<LeadTime>> getLeadTimeForTeam(
            @PathVariable String team,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(leadTimeService.getLeadTimeForTeam(team, startDate, endDate));
    }

    @PostMapping
    public ResponseEntity<LeadTime> createLeadTime(@RequestBody LeadTime leadTime) {
        return ResponseEntity.ok(leadTimeService.saveLeadTime(leadTime));
    }

    @GetMapping
    public ResponseEntity<List<LeadTime>> getAllLeadTimes() {
        return ResponseEntity.ok(leadTimeService.getAllLeadTimes());
    }
} 