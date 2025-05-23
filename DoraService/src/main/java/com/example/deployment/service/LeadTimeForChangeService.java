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

    public LeadTimeForChange calculateLeadTime(LeadTimeForChange request) {
        double leadTimeHours = ChronoUnit.HOURS.between(
                request.getCreatedDate(),
                request.getDeployedDate()
        );

        LeadTimeForChangeEntity entity = LeadTimeForChangeEntity.builder()
                .team(request.getTeam())
                .createdDate(request.getCreatedDate())
                .deployedDate(request.getDeployedDate())
                .leadTimeHours(leadTimeHours)
                .build();

        LeadTimeForChangeEntity savedEntity = repository.save(entity);
        return mapToModel(savedEntity);
    }

    public List<LeadTimeForChange> getTeamLeadTime(String team) {
        return repository.findByTeam(team).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    public List<LeadTimeForChange> getTeamLeadTimeInDateRange(
            String team, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByTeamAndCreatedDateBetween(team, startDate, endDate).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    public Double calculateAverageLeadTime(String team, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.calculateAverageLeadTime(team, startDate, endDate);
    }

    private LeadTimeForChange mapToModel(LeadTimeForChangeEntity entity) {
        return LeadTimeForChange.builder()
                .team(entity.getTeam())
                .createdDate(entity.getCreatedDate())
                .deployedDate(entity.getDeployedDate())
                .leadTimeHours(entity.getLeadTimeHours())
                .build();
    }
} 