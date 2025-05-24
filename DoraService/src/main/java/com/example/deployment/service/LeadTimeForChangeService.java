package com.example.deployment.service;

import com.example.deployment.entity.LeadTimeForChange;
import com.example.deployment.model.LeadTimeForChangeModel;
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

    public LeadTimeForChangeModel calculateLeadTime(LeadTimeForChangeModel request) {
        double leadTimeHours = ChronoUnit.HOURS.between(
                request.getCreatedDate(),
                request.getDeployedDate()
        );

        LeadTimeForChange entity = LeadTimeForChange.builder()
                .team(request.getTeam())
                .createdDate(request.getCreatedDate())
                .deployedDate(request.getDeployedDate())
                .leadTimeHours(leadTimeHours)
                .interval(request.getInterval())
                .build();

        LeadTimeForChange savedEntity = repository.save(entity);
        return mapToModel(savedEntity);
    }

    public List<LeadTimeForChangeModel> getTeamLeadTime(String team) {
        return repository.findByTeam(team).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    public List<LeadTimeForChangeModel> getTeamLeadTimeByInterval(String team, String interval) {
        return repository.findByTeamAndInterval(team, interval).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    public List<LeadTimeForChangeModel> getTeamLeadTimeInDateRange(
            String team, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByTeamAndCreatedDateBetween(team, startDate, endDate).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    public Double calculateAverageLeadTime(String team, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.calculateAverageLeadTime(team, startDate, endDate);
    }

    private LeadTimeForChangeModel mapToModel(LeadTimeForChange entity) {
        return LeadTimeForChangeModel.builder()
                .team(entity.getTeam())
                .createdDate(entity.getCreatedDate())
                .deployedDate(entity.getDeployedDate())
                .leadTimeHours(entity.getLeadTimeHours())
                .interval(entity.getInterval())
                .build();
    }
} 