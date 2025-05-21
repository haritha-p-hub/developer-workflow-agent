package com.example.deployment.repository;

import com.example.deployment.entity.LeadTimeChange;
import com.example.deployment.model.LeadTimeMetrics;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface LeadTimeRepository extends ElasticsearchRepository<LeadTimeChange, String> {
    LeadTimeMetrics getLeadTimeMetrics(LocalDate startDate, LocalDate endDate, String team, String interval);
} 