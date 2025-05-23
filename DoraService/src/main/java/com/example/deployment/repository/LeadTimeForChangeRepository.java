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
    
    List<LeadTimeForChangeEntity> findByTeamId(String teamId);
    
    List<LeadTimeForChangeEntity> findByTeamIdAndCommitTimeBetween(
            String teamId, LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT AVG(l.leadTimeInHours) FROM LeadTimeForChangeEntity l WHERE l.teamId = :teamId AND l.commitTime BETWEEN :startDate AND :endDate")
    Double calculateAverageLeadTime(
            @Param("teamId") String teamId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
} 