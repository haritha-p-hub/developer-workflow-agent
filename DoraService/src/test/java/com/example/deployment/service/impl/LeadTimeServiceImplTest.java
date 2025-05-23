package com.example.deployment.service.impl;

import com.example.deployment.model.LeadTimeMetrics;
import com.example.deployment.repository.LeadTimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeadTimeServiceImplTest {

    @Mock
    private LeadTimeRepository leadTimeRepository;

    @InjectMocks
    private LeadTimeServiceImpl leadTimeService;

    private LocalDate startDate;
    private LocalDate endDate;
    private String team;
    private String interval;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2024, 1, 1);
        endDate = LocalDate.of(2024, 1, 31);
        team = "team1";
        interval = "daily";
    }

    @Test
    void getLeadTimeMetrics_WithValidInputs_ShouldReturnMetrics() {
        // Arrange
        LeadTimeMetrics expectedMetrics = new LeadTimeMetrics();
        when(leadTimeRepository.getLeadTimeMetrics(any(), any(), any(), any()))
            .thenReturn(expectedMetrics);

        // Act
        LeadTimeMetrics result = leadTimeService.getLeadTimeMetrics(startDate, endDate, team, interval);

        // Assert
        assertNotNull(result);
        assertEquals(expectedMetrics, result);
        verify(leadTimeRepository).getLeadTimeMetrics(startDate, endDate, team, interval);
    }

    @Test
    void getLeadTimeMetrics_WithNullStartDate_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            leadTimeService.getLeadTimeMetrics(null, endDate, team, interval)
        );
    }

    @Test
    void getLeadTimeMetrics_WithNullEndDate_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            leadTimeService.getLeadTimeMetrics(startDate, null, team, interval)
        );
    }

    @Test
    void getLeadTimeMetrics_WithInvalidDateRange_ShouldThrowException() {
        // Arrange
        LocalDate invalidStartDate = LocalDate.of(2024, 1, 31);
        LocalDate invalidEndDate = LocalDate.of(2024, 1, 1);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            leadTimeService.getLeadTimeMetrics(invalidStartDate, invalidEndDate, team, interval)
        );
    }

    @Test
    void getLeadTimeMetrics_WithNullTeam_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            leadTimeService.getLeadTimeMetrics(startDate, endDate, null, interval)
        );
    }

    @Test
    void getLeadTimeMetrics_WithEmptyTeam_ShouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            leadTimeService.getLeadTimeMetrics(startDate, endDate, "", interval)
        );
    }

    @Test
    void getLeadTimeMetrics_WithInvalidInterval_ShouldThrowException() {
        // Arrange
        String invalidInterval = "invalid";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
            leadTimeService.getLeadTimeMetrics(startDate, endDate, team, invalidInterval)
        );
    }
} 