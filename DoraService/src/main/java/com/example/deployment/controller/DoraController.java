package com.example.deployment.controller;

import com.example.deployment.model.DeploymentFrequencyDto;
import com.example.deployment.model.LeadTimeForChangeDto;
import com.example.deployment.service.DoraService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DoraController {

    private final DoraService service;

    public DoraController(DoraService service) {
        this.service = service;
    }

    @GetMapping("/metrics/deployment-frequency")
    public List<DeploymentFrequencyDto> getDeploymentFrequency(@RequestParam String index) {
        return service.getDeploymentFrequency(index);
    }

    @GetMapping("/api/v1/metrics/lead-time")
    public List<LeadTimeForChangeDto> getLeadTimeForChange(
            @RequestParam String index,
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String team,
            @RequestParam(required = false, defaultValue = "weekly") String interval) {
        return service.getLeadTimeForChange(index, startDate, endDate, team, interval);
    }
}
