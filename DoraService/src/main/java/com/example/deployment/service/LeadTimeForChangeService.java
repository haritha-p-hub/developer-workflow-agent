package com.example.deployment.service;

import com.example.deployment.entity.LeadTimeForChangeEntity;
import com.example.deployment.model.LeadTimeForChange;
import com.example.deployment.repository.LeadTimeForChangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeadTimeForChangeService {

    private final LeadTimeForChangeRepository repository;

    public LeadTimeForChange saveLeadTimeForChange(LeadTimeForChange leadTimeForChange) {
        LeadTimeForChangeEntity entity = mapToEntity(leadTimeForChange);
        LeadTimeForChangeEntity savedEntity = repository.save(entity);
        return mapToModel(savedEntity);
    }

    public List<LeadTimeForChange> getLeadTimeForChangeByTeamId(String teamId) {
        return repository.findByTeamId(teamId)
                .stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    public List<LeadTimeForChange> getLeadTimeForChangeByTeamIdAndDateRange(
            String teamId, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByTeamIdAndCommitTimeBetween(teamId, startDate, endDate)
                .stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    public Double calculateAverageLeadTime(String teamId, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.calculateAverageLeadTime(teamId, startDate, endDate);
    }

    public LeadTimeForChange calculateAndSaveLeadTime(
            String teamId, String teamName, String commitId, LocalDateTime commitTime,
            String deploymentId, LocalDateTime deploymentTime, String status) {
        
        long leadTimeInHours = ChronoUnit.HOURS.between(commitTime, deploymentTime);
        
        LeadTimeForChange leadTimeForChange = LeadTimeForChange.builder()
                .teamId(teamId)
                .teamName(teamName)
                .commitId(commitId)
                .commitTime(commitTime)
                .deploymentId(deploymentId)
                .deploymentTime(deploymentTime)
                .leadTimeInHours(leadTimeInHours)
                .status(status)
                .build();
        
        return saveLeadTimeForChange(leadTimeForChange);
    }

    private LeadTimeForChangeEntity mapToEntity(LeadTimeForChange model) {
        return LeadTimeForChangeEntity.builder()
                .teamId(model.getTeamId())
                .teamName(model.getTeamName())
                .commitTime(model.getCommitTime())
                .deploymentTime(model.getDeploymentTime())
                .leadTimeInHours(model.getLeadTimeInHours())
                .commitId(model.getCommitId())
                .deploymentId(model.getDeploymentId())
                .status(model.getStatus())
                .build();
    }

    private LeadTimeForChange mapToModel(LeadTimeForChangeEntity entity) {
        return LeadTimeForChange.builder()
                .teamId(entity.getTeamId())
                .teamName(entity.getTeamName())
                .commitTime(entity.getCommitTime())
                .deploymentTime(entity.getDeploymentTime())
                .leadTimeInHours(entity.getLeadTimeInHours())
                .commitId(entity.getCommitId())
                .deploymentId(entity.getDeploymentId())
                .status(entity.getStatus())
                .build();
    }
} 