package com.example.deployment.service.impl;

import com.example.deployment.entity.DeploymentFrequencyEntity;
import com.example.deployment.entity.LeadTimeForChangeEntity;
import com.example.deployment.model.DeploymentFrequencyDto;
import com.example.deployment.model.LeadTimeForChangeDto;
import com.example.deployment.repository.DoraRepository;
import com.example.deployment.service.DoraService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoraServiceImpl implements DoraService {

    private final DoraRepository repository;

    public DoraServiceImpl(DoraRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DeploymentFrequencyDto> getDeploymentFrequency(String index) {
        List<DeploymentFrequencyEntity> entities = repository.fetchDeploymentFrequency(index);
        return entities.stream()
                .map(e -> new DeploymentFrequencyDto(e.getDay(), e.getCount()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LeadTimeForChangeDto> getLeadTimeForChange(String index, String startDate, String endDate, String team, String interval) {
        List<LeadTimeForChangeEntity> entities = repository.fetchLeadTimeForChange(index, startDate, endDate, team, interval);
        return entities.stream()
                .map(e -> new LeadTimeForChangeDto(e.getInterval(), e.getLeadTime(), e.getChanges()))
                .collect(Collectors.toList());
    }

} 