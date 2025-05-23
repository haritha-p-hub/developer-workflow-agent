package com.example.deployment.repository.impl;

import com.example.deployment.entity.LeadTimeForChangeEntity;
import com.example.deployment.repository.LeadTimeForChangeRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class LeadTimeForChangeRepositoryImpl extends SimpleJpaRepository<LeadTimeForChangeEntity, Long> implements LeadTimeForChangeRepository {

    private final EntityManager entityManager;

    public LeadTimeForChangeRepositoryImpl(EntityManager entityManager) {
        super(LeadTimeForChangeEntity.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<LeadTimeForChangeEntity> findByTeam(String team) {
        return entityManager.createQuery(
                "SELECT l FROM LeadTimeForChangeEntity l WHERE l.team = :team", LeadTimeForChangeEntity.class)
                .setParameter("team", team)
                .getResultList();
    }

    @Override
    public List<LeadTimeForChangeEntity> findByTeamAndCreatedDateBetween(
            String team, LocalDateTime startDate, LocalDateTime endDate) {
        return entityManager.createQuery(
                "SELECT l FROM LeadTimeForChangeEntity l WHERE l.team = :team AND l.createdDate BETWEEN :startDate AND :endDate",
                LeadTimeForChangeEntity.class)
                .setParameter("team", team)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public List<LeadTimeForChangeEntity> findByTeamAndInterval(
            String team, String interval) {
        return entityManager.createQuery(
                "SELECT l FROM LeadTimeForChangeEntity l WHERE l.team = :team AND l.interval = :interval",
                LeadTimeForChangeEntity.class)
                .setParameter("team", team)
                .setParameter("interval", interval)
                .getResultList();
    }

    @Override
    public Double calculateAverageLeadTime(String team, LocalDateTime startDate, LocalDateTime endDate) {
        return entityManager.createQuery(
                "SELECT AVG(l.leadTimeHours) FROM LeadTimeForChangeEntity l WHERE l.team = :team AND l.createdDate BETWEEN :startDate AND :endDate",
                Double.class)
                .setParameter("team", team)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getSingleResult();
    }
} 