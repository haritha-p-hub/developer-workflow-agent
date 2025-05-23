package com.example.deployment.controller;

import com.example.deployment.dto.LeadTimeForChangeDTO;
import com.example.deployment.service.LeadTimeForChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lead-time")
public class LeadTimeForChangeController {

    @Autowired
    private LeadTimeForChangeService service;

    @GetMapping
    public ResponseEntity<List<LeadTimeForChangeDTO>> getLeadTimes(
            @RequestParam String team,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String interval
    ) {
        List<LeadTimeForChangeDTO> leadTimes = service.getLeadTimes(team, startDate, endDate, interval);
        return ResponseEntity.ok(leadTimes);
    }
} 