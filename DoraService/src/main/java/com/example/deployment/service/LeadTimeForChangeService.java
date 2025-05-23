package com.example.deployment.service;

import com.example.deployment.dto.LeadTimeForChangeDTO;
import java.util.List;

public interface LeadTimeForChangeService {
    List<LeadTimeForChangeDTO> getLeadTimes(String team, String startDate, String endDate, String interval);
} 