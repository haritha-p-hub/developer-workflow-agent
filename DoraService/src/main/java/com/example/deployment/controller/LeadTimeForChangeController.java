package com.example.deployment.controller;

import com.example.deployment.model.LeadTimeForChange;
import com.example.deployment.service.LeadTimeForChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/lead-time-for-change")
@RequiredArgsConstructor
public class LeadTimeForChangeController {

    private final LeadTimeForChangeService service;

    @PostMapping
    public ResponseEntity<LeadTimeForChange> createLeadTimeForChange(@RequestBody LeadTimeForChange leadTimeForChange) {
        return ResponseEntity.ok(service.saveLeadTimeForChange(leadTimeForChange));
    }

    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<LeadTimeForChange>> getLeadTimeForChangeByTeamId(@PathVariable String teamId) {
        return ResponseEntity.ok(service.getLeadTimeForChangeByTeamId(teamId));
    }

    @GetMapping("/team/{teamId}/date-range")
    public ResponseEntity<List<LeadTimeForChange>> getLeadTimeForChangeByTeamIdAndDateRange(
            @PathVariable String teamId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(service.getLeadTimeForChangeByTeamIdAndDateRange(teamId, startDate, endDate));
    }

    @GetMapping("/team/{teamId}/average")
    public ResponseEntity<Double> calculateAverageLeadTime(
            @PathVariable String teamId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(service.calculateAverageLeadTime(teamId, startDate, endDate));
    }

    @PostMapping("/calculate")
    public ResponseEntity<LeadTimeForChange> calculateAndSaveLeadTime(
            @RequestParam String teamId,
            @RequestParam String teamName,
            @RequestParam String commitId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime commitTime,
            @RequestParam String deploymentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deploymentTime,
            @RequestParam String status) {
        return ResponseEntity.ok(service.calculateAndSaveLeadTime(
                teamId, teamName, commitId, commitTime, deploymentId, deploymentTime, status));
    }
} 