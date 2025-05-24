package com.example.deployment.controller;

import com.example.deployment.model.LeadTimeForChangeModel;
import com.example.deployment.service.LeadTimeForChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lead-time")
@RequiredArgsConstructor
public class LeadTimeForChangeController {

    private final LeadTimeForChangeService service;

    @PostMapping("/calculate")
    public ResponseEntity<LeadTimeForChangeModel> calculateLeadTime(@RequestBody LeadTimeForChangeModel request) {
        return ResponseEntity.ok(service.calculateLeadTime(request));
    }

    @GetMapping("/team/{team}")
    public ResponseEntity<List<LeadTimeForChangeModel>> getTeamLeadTime(@PathVariable String team) {
        return ResponseEntity.ok(service.getTeamLeadTime(team));
    }

    @GetMapping("/team/{team}/interval/{interval}")
    public ResponseEntity<List<LeadTimeForChangeModel>> getTeamLeadTimeByInterval(
            @PathVariable String team,
            @PathVariable String interval) {
        return ResponseEntity.ok(service.getTeamLeadTimeByInterval(team, interval));
    }

    @GetMapping("/team/{team}/range")
    public ResponseEntity<List<LeadTimeForChangeModel>> getTeamLeadTimeInDateRange(
            @PathVariable String team,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(service.getTeamLeadTimeInDateRange(team, startDate, endDate));
    }

    @GetMapping("/team/{team}/average")
    public ResponseEntity<Double> calculateAverageLeadTime(
            @PathVariable String team,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(service.calculateAverageLeadTime(team, startDate, endDate));
    }
} 