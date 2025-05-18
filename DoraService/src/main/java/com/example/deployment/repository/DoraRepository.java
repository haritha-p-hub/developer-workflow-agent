package com.example.deployment.repository;

import com.example.deployment.entity.DeploymentFrequencyEntity;
import com.example.deployment.entity.LeadTimeForChangeEntity;
import java.util.List;

public interface DoraRepository {
    List<DeploymentFrequencyEntity> fetchDeploymentFrequency(String index);
    List<LeadTimeForChangeEntity> fetchLeadTimeForChange(String index, String startDate, String endDate, String team, String interval);
}
