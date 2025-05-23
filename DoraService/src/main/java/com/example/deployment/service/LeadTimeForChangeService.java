package com.example.deployment.service;

import com.example.deployment.model.LeadTimeForChange;

import java.time.LocalDateTime;
import java.util.List;

public interface LeadTimeForChangeService {
    LeadTimeForChange calculateLeadTime(LeadTimeForChange request);
    
    List<LeadTimeForChange> getTeamLeadTime(String team);
    
    List<LeadTimeForChange> getTeamLeadTimeByInterval(String team, String interval);
    
    List<LeadTimeForChange> getTeamLeadTimeInDateRange(String team, LocalDateTime startDate, LocalDateTime endDate);
    
    Double calculateAverageLeadTime(String team, LocalDateTime startDate, LocalDateTime endDate);
} 