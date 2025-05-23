package com.example.deployment.service.impl;

import com.example.deployment.dto.LeadTimeForChangeDTO;
import com.example.deployment.entity.LeadTimeForChangeEntity;
import com.example.deployment.repository.LeadTimeForChangeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LeadTimeForChangeServiceImplTest {

    @Mock
    private LeadTimeForChangeRepository repository;

    @InjectMocks
    private LeadTimeForChangeServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLeadTimes_WithAllParameters_ShouldReturnFilteredResults() {
        // Arrange
        String team = "TeamA";
        String startDate = "2024-01-01";
        String endDate = "2024-01-31";
        String interval = "week";

        LeadTimeForChangeEntity entity1 = new LeadTimeForChangeEntity(1L, team, 
            LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-07"), interval, 24.0);
        LeadTimeForChangeEntity entity2 = new LeadTimeForChangeEntity(2L, team, 
            LocalDate.parse("2024-01-08"), LocalDate.parse("2024-01-14"), interval, 36.0);

        when(repository.findByTeamAndStartDateBetweenAndInterval(
            eq(team), 
            any(LocalDate.class), 
            any(LocalDate.class), 
            eq(interval)
        )).thenReturn(Arrays.asList(entity1, entity2));

        // Act
        List<LeadTimeForChangeDTO> result = service.getLeadTimes(team, startDate, endDate, interval);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(team, result.get(0).getTeam());
        assertEquals(interval, result.get(0).getInterval());
        verify(repository).findByTeamAndStartDateBetweenAndInterval(
            eq(team), 
            any(LocalDate.class), 
            any(LocalDate.class), 
            eq(interval)
        );
    }

    @Test
    void getLeadTimes_WithTeamOnly_ShouldReturnAllTeamResults() {
        // Arrange
        String team = "TeamA";
        LeadTimeForChangeEntity entity = new LeadTimeForChangeEntity(1L, team, 
            LocalDate.now(), LocalDate.now(), "week", 24.0);

        when(repository.findByTeam(team)).thenReturn(Arrays.asList(entity));

        // Act
        List<LeadTimeForChangeDTO> result = service.getLeadTimes(team, null, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(team, result.get(0).getTeam());
        verify(repository).findByTeam(team);
    }

    @Test
    void getLeadTimes_WithTeamAndInterval_ShouldReturnFilteredResults() {
        // Arrange
        String team = "TeamA";
        String interval = "month";
        LeadTimeForChangeEntity entity = new LeadTimeForChangeEntity(1L, team, 
            LocalDate.now(), LocalDate.now(), interval, 24.0);

        when(repository.findByTeamAndInterval(team, interval)).thenReturn(Arrays.asList(entity));

        // Act
        List<LeadTimeForChangeDTO> result = service.getLeadTimes(team, null, null, interval);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(team, result.get(0).getTeam());
        assertEquals(interval, result.get(0).getInterval());
        verify(repository).findByTeamAndInterval(team, interval);
    }
} 