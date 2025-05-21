package com.example.deployment.service;

import com.example.deployment.model.LeadTimeMetrics;
import java.time.LocalDate;

public interface LeadTimeService {
    LeadTimeMetrics getLeadTimeMetrics(LocalDate startDate, LocalDate endDate, String team, String interval);
} 