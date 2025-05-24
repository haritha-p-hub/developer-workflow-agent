package com.example.deployment.repository.impl;

import com.example.deployment.entity.LeadTimeForChange;
import com.example.deployment.repository.LeadTimeForChangeRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class LeadTimeForChangeRepositoryImpl extends SimpleJpaRepository<LeadTimeForChange, Long> implements LeadTimeForChangeRepository {

    private final EntityManager entityManager;

    public LeadTimeForChangeRepositoryImpl(EntityManager entityManager) {
        super(LeadTimeForChange.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<LeadTimeForChange> findByTeam(String team) {
        return entityManager.createQuery(
                "SELECT l FROM LeadTimeForChange l WHERE l.team = :team", LeadTimeForChange.class)
                .setParameter("team", team)
                .getResultList();
    }

    @Override
    public List<LeadTimeForChange> findByTeamAndCreatedDateBetween(
            String team, LocalDateTime startDate, LocalDateTime endDate) {
        return entityManager.createQuery(
                "SELECT l FROM LeadTimeForChange l WHERE l.team = :team AND l.createdDate BETWEEN :startDate AND :endDate",
                LeadTimeForChange.class)
                .setParameter("team", team)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public List<LeadTimeForChange> findByTeamAndInterval(
            String team, String interval) {
        return entityManager.createQuery(
                "SELECT l FROM LeadTimeForChange l WHERE l.team = :team AND l.interval = :interval",
                LeadTimeForChange.class)
                .setParameter("team", team)
                .setParameter("interval", interval)
                .getResultList();
    }

    @Override
    public Double calculateAverageLeadTime(String team, LocalDateTime startDate, LocalDateTime endDate) {
        return entityManager.createQuery(
                "SELECT AVG(l.leadTimeHours) FROM LeadTimeForChange l WHERE l.team = :team AND l.createdDate BETWEEN :startDate AND :endDate",
                Double.class)
                .setParameter("team", team)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }
} 