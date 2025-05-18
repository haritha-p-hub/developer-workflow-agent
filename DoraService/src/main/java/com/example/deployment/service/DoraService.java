package com.example.deployment.service;

import com.example.deployment.model.DeploymentFrequencyDto;
import com.example.deployment.model.LeadTimeForChangeDto;
import java.util.List;

public interface DoraService {
    List<DeploymentFrequencyDto> getDeploymentFrequency(String index);
    List<LeadTimeForChangeDto> getLeadTimeForChange(String index, String startDate, String endDate, String team, String interval);
  }



