package com.example.deployment.service;

import com.example.deployment.entity.LeadTime;
import com.example.deployment.repository.LeadTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeadTimeServiceImpl implements LeadTimeService {

    private final LeadTimeRepository leadTimeRepository;

    @Autowired
    public LeadTimeServiceImpl(LeadTimeRepository leadTimeRepository) {
        this.leadTimeRepository = leadTimeRepository;
    }

    @Override
    public List<LeadTime> getLeadTimeForTeam(String team, LocalDateTime startDate, LocalDateTime endDate) {
        return leadTimeRepository.findByTeamAndDateRange(team, startDate, endDate);
    }

    @Override
    public LeadTime saveLeadTime(LeadTime leadTime) {
        return leadTimeRepository.save(leadTime);
    }

    @Override
    public List<LeadTime> getAllLeadTimes() {
        return leadTimeRepository.findAll();
    }
} 