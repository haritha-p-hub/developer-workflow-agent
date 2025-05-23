package com.example.deployment.repository;

import com.example.deployment.entity.LeadTimeForChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeadTimeForChangeRepository extends JpaRepository<LeadTimeForChangeEntity, Long> {
    List<LeadTimeForChangeEntity> findByTeam(String team);
    
    List<LeadTimeForChangeEntity> findByTeamAndCreatedDateBetween(String team, LocalDateTime startDate, LocalDateTime endDate);
    
    List<LeadTimeForChangeEntity> findByTeamAndInterval(String team, String interval);
    
    @Query("SELECT AVG(l.leadTimeHours) FROM LeadTimeForChangeEntity l WHERE l.team = ?1 AND l.createdDate BETWEEN ?2 AND ?3")
    Double calculateAverageLeadTime(String team, LocalDateTime startDate, LocalDateTime endDate);
} 