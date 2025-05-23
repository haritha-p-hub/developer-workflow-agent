package com.example.deployment.service.impl;

import com.example.deployment.dto.LeadTimeForChangeDTO;
import com.example.deployment.entity.LeadTimeForChangeEntity;
import com.example.deployment.model.LeadTimeForChange;
import com.example.deployment.repository.LeadTimeForChangeRepository;
import com.example.deployment.service.LeadTimeForChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeadTimeForChangeServiceImpl implements LeadTimeForChangeService {
    
    private final LeadTimeForChangeRepository repository;
    
    @Override
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
                .interval(request.getInterval())
                .build();
        
        LeadTimeForChangeEntity savedEntity = repository.save(entity);
        return mapToModel(savedEntity);
    }
    
    @Override
    public List<LeadTimeForChange> getTeamLeadTime(String team) {
        return repository.findByTeam(team).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<LeadTimeForChange> getTeamLeadTimeByInterval(String team, String interval) {
        return repository.findByTeamAndInterval(team, interval).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<LeadTimeForChange> getTeamLeadTimeInDateRange(String team, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.findByTeamAndCreatedDateBetween(team, startDate, endDate).stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }
    
    @Override
    public Double calculateAverageLeadTime(String team, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.calculateAverageLeadTime(team, startDate, endDate);
    }
    
    private LeadTimeForChange mapToModel(LeadTimeForChangeEntity entity) {
        return LeadTimeForChange.builder()
                .team(entity.getTeam())
                .createdDate(entity.getCreatedDate())
                .deployedDate(entity.getDeployedDate())
                .leadTimeHours(entity.getLeadTimeHours())
                .interval(entity.getInterval())
                .build();
    }
    
    private LeadTimeForChangeDTO mapToDto(LeadTimeForChange model) {
        return LeadTimeForChangeDTO.builder()
                .team(model.getTeam())
                .createdDate(model.getCreatedDate())
                .deployedDate(model.getDeployedDate())
                .leadTimeHours(model.getLeadTimeHours())
                .interval(model.getInterval())
                .status("SUCCESS")
                .build();
    }
} 