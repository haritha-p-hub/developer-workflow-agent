package com.example.deployment.repository;

import com.example.deployment.entity.LeadTimeForChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeadTimeForChangeRepository extends JpaRepository<LeadTimeForChangeEntity, Long> {
    
    List<LeadTimeForChangeEntity> findByTeam(String team);
    
    List<LeadTimeForChangeEntity> findByTeamAndCreatedDateBetween(
            String team, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT AVG(l.leadTimeHours) FROM LeadTimeForChangeEntity l WHERE l.team = :team AND l.createdDate BETWEEN :startDate AND :endDate")
    Double calculateAverageLeadTime(
            @Param("team") String team,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
} 