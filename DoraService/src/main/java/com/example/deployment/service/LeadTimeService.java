package com.example.deployment.service;

import com.example.deployment.entity.LeadTime;
import java.time.LocalDateTime;
import java.util.List;

public interface LeadTimeService {
    List<LeadTime> getLeadTimeForTeam(String team, LocalDateTime startDate, LocalDateTime endDate);
    LeadTime saveLeadTime(LeadTime leadTime);
    List<LeadTime> getAllLeadTimes();
} 