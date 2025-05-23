package com.example.deployment.repository;

import com.example.deployment.entity.LeadTimeForChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeadTimeForChangeRepository extends JpaRepository<LeadTimeForChangeEntity, Long> {
    List<LeadTimeForChangeEntity> findByTeam(String team);
    List<LeadTimeForChangeEntity> findByTeamAndInterval(String team, String interval);
    List<LeadTimeForChangeEntity> findByTeamAndCreatedDateBetween(String team, LocalDate startDate, LocalDate endDate);
    List<LeadTimeForChangeEntity> findByTeamAndCreatedDateBetweenAndInterval(String team, LocalDate startDate, LocalDate endDate, String interval);
} 