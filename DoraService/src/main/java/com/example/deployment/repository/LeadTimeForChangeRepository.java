package com.example.deployment.repository;

import com.example.deployment.entity.LeadTimeForChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeadTimeForChangeRepository extends JpaRepository<LeadTimeForChange, Long> {
    List<LeadTimeForChange> findByTeam(String team);
    List<LeadTimeForChange> findByTeamAndCreatedDateBetween(String team, LocalDateTime startDate, LocalDateTime endDate);
    List<LeadTimeForChange> findByTeamAndInterval(String team, String interval);
    Double calculateAverageLeadTime(String team, LocalDateTime startDate, LocalDateTime endDate);
} 