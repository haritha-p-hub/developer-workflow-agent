package com.example.deployment.repository;

import com.example.deployment.entity.LeadTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeadTimeRepository extends JpaRepository<LeadTime, Long> {
    
    @Query("SELECT l FROM LeadTime l WHERE l.team = :team AND l.startDate >= :startDate AND l.endDate <= :endDate")
    List<LeadTime> findByTeamAndDateRange(
        @Param("team") String team,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );
    
    List<LeadTime> findByTeam(String team);
} 