package com.example.deployment.service.impl;

import com.example.deployment.model.LeadTimeMetrics;
import com.example.deployment.repository.LeadTimeRepository;
import com.example.deployment.service.LeadTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LeadTimeServiceImpl implements LeadTimeService {

    @Autowired
    private LeadTimeRepository leadTimeRepository;

    @Override
    public LeadTimeMetrics getLeadTimeMetrics(LocalDate startDate, LocalDate endDate, String team, String interval) {
        validateInputs(startDate, endDate, team, interval);
        return leadTimeRepository.getLeadTimeMetrics(startDate, endDate, team, interval);
    }

    private void validateInputs(LocalDate startDate, LocalDate endDate, String team, String interval) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date are required");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        if (team == null || team.trim().isEmpty()) {
            throw new IllegalArgumentException("Team is required");
        }
        if (interval == null || !isValidInterval(interval)) {
            throw new IllegalArgumentException("Interval must be one of: daily, weekly, monthly");
        }
    }

    private boolean isValidInterval(String interval) {
        return interval.equalsIgnoreCase("daily") ||
               interval.equalsIgnoreCase("weekly") ||
               interval.equalsIgnoreCase("monthly");
    }
} 